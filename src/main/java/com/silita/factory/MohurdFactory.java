package com.silita.factory;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.silita.common.Constant;
import com.silita.consumer.RedisUtils;
import com.silita.consumer.RedisUtilsAlone;
import com.silita.service.IAptitudeCleanService;
import com.silita.service.MohurdService;
import com.silita.spider.common.model.*;
import com.silita.utils.BeanUtils;
import com.silita.utils.DateTimeUtils;
import com.silita.utils.Pinyin;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author:chenzhiqiang
 * @Date:2018/7/5 21:33
 * @Description:全国四库一数据接收并处理工厂
 */
@Component
public class MohurdFactory extends AbstractFactory {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtils redisUtils;
    //private RedisUtilsAlone redisUtils = new RedisUtilsAlone();

    @Autowired
    private MohurdService mohurdService;

    @Autowired
    private IAptitudeCleanService aptitudeCleanService;

    @Override
    public void process(Object object) {
        if (object instanceof Company) {
            Company company = (Company) object;
            String com_id = company.getCom_id();
            String md5 = company.getMd5();
            String com_name = company.getCom_name();
            com_name = com_name.replaceAll("（|）| |:|：|，|,|;|,|(|)|·|、", "").trim();//去除特殊符号
            company.setCom_name_py(Pinyin.getPinYinFirstChar(com_name));//得到拼音首字母
            boolean exists = redisUtils.hexists(Constant.Cache_Company, md5);
            if (!exists) {//实体MD5不存在
                String url = company.getUrl();
                url = url.replace("jzsc2016.mohurd.gov.cn","jzsc.mohurd.gov.cn");
                if(url!=null){
                    try {
                        Integer count = mohurdService.countCompanyByUrl(url);
                        if(count==0){
                            int result = mohurdService.insertCompany(company);//新增
                            if (result > 0) {
                                logger.info(String.format("新增企业基本信息【%s】【%s】成功！", com_id,com_name));
                            }else{
                                logger.info(String.format("新增企业基本信息【%s】【%s】失败！", com_id,com_name));
                            }
                        }else if(count==1){
                            updateCompanyToUrl(company);
                        }else if(count>1){
                            mohurdService.deleteCompanyForUrl(url);
                            int result = mohurdService.insertCompany(company);//新增
                            if (result > 0) {
                                logger.info(String.format("新增企业基本信息【%s】【%s】成功！", com_id,com_name));
                            }else{
                                logger.info(String.format("新增企业基本信息【%s】【%s】失败！", com_id,com_name));
                            }
                        }
                    } catch (Exception e) {
                        if (e instanceof MySQLIntegrityConstraintViolationException) {
                            //忽略主键冲突异常
                        } else {
                            logger.warn(ExceptionUtils.getMessage(e));
                        }
                    }
                    redisUtils.hset(Constant.Cache_Company_Url, url, DateTimeUtils.current());
                }
                redisUtils.hset(Constant.Cache_Company, md5, DateTimeUtils.current());
            } else {
                logger.info(String.format("[查Redis缓存]【%s】【%s】企业基本信息实体MD5已存在，不做任何操作", md5,com_name));
                //企业信息更新updated，擦亮一下，证明更新过 --张夏晖2019-05-16
                mohurdService.updateCompanyForUpdated(com_id);
            }
        } else if (object instanceof CompanyQualification) {
            CompanyQualification qualification = (CompanyQualification) object;
            String pkid = qualification.getPkid();
            String md5 = qualification.getMd5();
            logger.info("====企业资质ETL=【"+qualification.getCom_name()+"】更新资质【"+qualification.getTotal()+"】条======");
            redisUtils.hset(Constant.Cache_CompanyQual_Num, qualification.getCom_name(), qualification.getTotal()+"|"+DateTimeUtils.current());
            boolean exists = redisUtils.hexists(Constant.Cache_CompanyQual, md5);
            if (!exists) {//实体MD5不存在
                try {
                    if(qualification.getCom_id()!=null&&qualification.getCert_no()!=null){
                        String id = mohurdService.selectCompanyQualification(qualification);
                        if (StringUtils.isBlank(id)) {
                            int result = mohurdService.insertCompanyQualification(qualification);//新增
                            if (result > 0) {
                                logger.info(String.format("新增企业资质 %s", pkid));
                                //洗资质
                                aptitudeCleanService.splitCompanyAptitudeByCompanyId(qualification.getCom_id());
                                aptitudeCleanService.updateCompanyAptitude(qualification.getCom_id());
                            }
                        } else {
                            qualification.setPkid(id);
                            updateCompanyQual(qualification);
                        }
                    }else{
                        logger.info(String.format("企业资质更新 [%s][%s] 缺失企业ID或资质编号", qualification.getCom_name(),qualification.getQual_name()));
                    }

                } catch (Exception e) {
                    if (e instanceof MySQLIntegrityConstraintViolationException) {
                        //忽略主键冲突异常
                    } else {
                        logger.warn(ExceptionUtils.getMessage(e));
                    }
                }
                redisUtils.hset(Constant.Cache_CompanyQual, md5, "");
            } else {
                logger.info(String.format("[查Redis缓存] %s 企业资质实体MD5已存在，不做任何操作", md5));
                //企业资质信息更新updated，擦亮一下，证明更新过 --张夏晖2019-05-16
                mohurdService.updateCompanyQualificationForUpdated(pkid);
            }
        } else if (object instanceof Project) {
            Project project = (Project) object;
            String proId = project.getPro_id();
            String md5 = project.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_Project, md5);
            if (!exists) {//实体MD5不存在
                try {
                    String id = mohurdService.selectProject(project);
                    if (StringUtils.isBlank(id)) {
                        int result = mohurdService.insertProject(project);//新增
                        if (result > 0) {
                            logger.info(String.format("新增项目 %s", proId));
                        }
                    } else {
                        project.setPro_id(id);
                        updateProject(project);
                    }
                } catch (Exception e) {
                    if (e instanceof MySQLIntegrityConstraintViolationException) {
                        //忽略主键冲突异常
                    } else {
                        logger.warn(ExceptionUtils.getMessage(e));
                    }
                }
                redisUtils.hset(Constant.Cache_Project, md5, "");
            } else {
                logger.info(String.format("[查Redis缓存] %s 项目实体MD5已存在，不做任何操作", md5));
                //企业业绩信息更新updated，擦亮一下，证明更新过 --张夏晖2019-08-20
                mohurdService.updateProjectForUpdated(proId);
            }
        } else if (object instanceof Person) {
            Person person = (Person) object;
            String pkid = person.getPkid();
            String md5 = person.getMd5();
            logger.info("====企业注册人员ETL=【"+person.getCom_name()+"】更新人员【"+person.getTotal()+"】条======");
            redisUtils.hset(Constant.Cache_Person_Num, person.getCom_name(), person.getTotal()+"|"+DateTimeUtils.current());
            boolean exists = redisUtils.hexists(Constant.Cache_Person, md5);
            if (!exists) {//实体MD5不存在
                try {
                    String id = mohurdService.selectPerson(person);
                    if (StringUtils.isBlank(id)) {
                        String result = mohurdService.insertPerson(person);//新增
                        if (StringUtils.isNotBlank(result)) {
                            logger.info(String.format("新增人员 %s", pkid));
                        }
                    } else {
                        person.setPkid(id);
                        updatePerson(person);
                    }
                } catch (Exception e) {
                    if (e instanceof MySQLIntegrityConstraintViolationException) {
                        //忽略主键冲突异常
                    } else {
                        logger.warn(ExceptionUtils.getMessage(e));
                    }
                }
                redisUtils.hset(Constant.Cache_Person, md5, "");
            } else {
                logger.info(String.format("[查Redis缓存] %s 人员实体MD5已存在，不做任何操作", md5));
                //企业注册人员信息更新updated，擦亮一下，证明更新过 --张夏晖2019-05-16
                mohurdService.updatePersonForUpdated(person);
            }
        } else if (object instanceof PersonChange) {
            PersonChange change = (PersonChange) object;
            String pkid = change.getPkid();
            String md5 = change.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_PersonChange, md5);
            if (!exists) {//实体MD5不存在，新增，人员变更不涉及到更新操作
                try {
                    String id = mohurdService.selectPersonChange(change);
                    if (StringUtils.isBlank(id)) {
                        int result = mohurdService.insertPersonChange(change);//新增
                        if (result > 0) {
                            logger.info(String.format("新增人员变更 %s", pkid));
                            redisUtils.hset(Constant.Cache_PersonChange, md5, "");
                        }
                    }
                } catch (Exception e) {
                    if (e instanceof MySQLIntegrityConstraintViolationException) {
                        //忽略主键冲突异常
                    } else {
                        logger.warn(ExceptionUtils.getMessage(e));
                    }
                }
            } else {
                logger.info(String.format("[查Redis缓存] %s 人员变更实体MD5已存在，不做任何操作", md5));
            }
        } else if (object instanceof PersonProject) {
            PersonProject personProject = (PersonProject) object;
            String pkid = personProject.getPkid();
            String md5 = personProject.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_PersonProject, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_PersonProject, pkid);
                if (!exists) {//主键md5不存在，新增
                    try {
                        String id = mohurdService.selectPersonProject(personProject);
                        if (StringUtils.isBlank(id)) {
                            int result = mohurdService.insertPersonProject(personProject);//新增
                            if (result > 0) {
                                logger.info(String.format("新增人员项目关系 %s", pkid));
                            }
                        } else {
                            personProject.setPkid(id);
                            updatePersonProject(personProject);
                        }
                    } catch (Exception e) {
                        if (e instanceof MySQLIntegrityConstraintViolationException) {
                            //忽略主键冲突异常
                        } else {
                            logger.warn(ExceptionUtils.getMessage(e));
                        }
                    }
                    redisUtils.hset(Constant.Cache_PersonProject, pkid, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    updatePersonProject(personProject);
                }
                redisUtils.hset(Constant.Cache_PersonProject, md5, "");
            } else {
                logger.info(String.format("[查Redis缓存] %s 人员项目关系实体MD5已存在，不做任何操作", md5));
            }
        } else if (object instanceof ProjectCompany) {
            ProjectCompany projectCompany = (ProjectCompany) object;
            String pkid = projectCompany.getPkid();
            String md5 = projectCompany.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_ProjectCompany, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_ProjectCompany, pkid);
                if (!exists) {//主键md5不存在，新增
                    try {
                        String id = mohurdService.selectProjectCompany(projectCompany);
                        if (StringUtils.isBlank(id)) {
                            int result = mohurdService.insertProjectCompany(projectCompany);//新增
                            if (result > 0) {
                                logger.info(String.format("新增项目企业关系 %s", pkid));
                            }
                        } else {
                            projectCompany.setPkid(pkid);
                            updateProjectCompany(projectCompany);
                        }
                    } catch (Exception e) {
                        if (e instanceof MySQLIntegrityConstraintViolationException) {
                            //忽略主键冲突异常
                        } else {
                            logger.warn(ExceptionUtils.getMessage(e));
                        }
                    }
                    redisUtils.hset(Constant.Cache_ProjectCompany, pkid, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    updateProjectCompany(projectCompany);
                }
                redisUtils.hset(Constant.Cache_ProjectCompany, md5, "");
            } else {
                logger.info(String.format("[查Redis缓存] %s 项目企业关系实体MD5已存在，不做任何操作", md5));
            }
        } else if (object instanceof ZhaoTouBiao) {
            ZhaoTouBiao zhaoTouBiao = (ZhaoTouBiao) object;
            String pkid = zhaoTouBiao.getPkid();
            //String md5 = zhaoTouBiao.getMd5();
            try {
                String id = mohurdService.selectZhaoTouBiao(zhaoTouBiao);
                if (StringUtils.isBlank(id)) {
                    int result = mohurdService.insertZhaoTouBiao(zhaoTouBiao);//新增
                    if (result > 0) {
                        logger.info(String.format("新增招投标 %s", pkid));
                    }
                } else {
                    zhaoTouBiao.setPkid(id);
                    updateZhaoTouBiao(zhaoTouBiao);
                }
            } catch (Exception e) {
                if (e instanceof MySQLIntegrityConstraintViolationException) {
                    //忽略主键冲突异常
                } else {
                    logger.warn(ExceptionUtils.getMessage(e));
                }
            }
        } else if (object instanceof ShiGongTuShenCha) {
            ShiGongTuShenCha shiGongTuShenCha = (ShiGongTuShenCha) object;
            String pkid = shiGongTuShenCha.getPkid();
            //String md5 = shiGongTuShenCha.getMd5();
            try {
                String id = mohurdService.selectShiGongTuShenCha(shiGongTuShenCha);
                if (StringUtils.isBlank(id)) {
                    int result = mohurdService.insertShiGongTuShenCha(shiGongTuShenCha);//新增
                    if (result > 0) {
                        logger.info(String.format("新增施工图审查 %s", pkid));
                    }
                } else {
                    shiGongTuShenCha.setPkid(id);
                    updateShiGongTuShenCha(shiGongTuShenCha);
                }
            } catch (Exception e) {
                if (e instanceof MySQLIntegrityConstraintViolationException) {
                    //忽略主键冲突异常
                } else {
                    logger.warn(ExceptionUtils.getMessage(e));
                }
            }
        } else if (object instanceof ShiGongXuKe) {
            ShiGongXuKe shiGongXuKe = (ShiGongXuKe) object;
            String pkid = shiGongXuKe.getPkid();
            //String md5 = shiGongXuKe.getMd5();
            try {
                String id = mohurdService.selectShiGongXuKe(shiGongXuKe);
                if (StringUtils.isBlank(id)) {
                    int result = mohurdService.insertShiGongXuKe(shiGongXuKe);//新增
                    if (result > 0) {
                        logger.info(String.format("新增施工许可 %s", pkid));
                    }
                } else {
                    shiGongXuKe.setPkid(id);
                    updateShiGongXuKe(shiGongXuKe);
                }
            } catch (Exception e) {
                if (e instanceof MySQLIntegrityConstraintViolationException) {
                    //忽略主键冲突异常
                } else {
                    logger.warn(ExceptionUtils.getMessage(e));
                }
            }
        } else if (object instanceof HeTongBeiAn) {
            HeTongBeiAn heTongBeiAn = (HeTongBeiAn) object;
            String pkid = heTongBeiAn.getPkid();
            //String md5 = heTongBeiAn.getMd5();
            try {
                String id = mohurdService.selectHeTongBeiAn(heTongBeiAn);
                if (StringUtils.isBlank(id)) {
                    int result = mohurdService.insertHeTongBeiAn(heTongBeiAn);//新增
                    if (result > 0) {
                        logger.info(String.format("新增合同备案 %s", pkid));
                    }
                } else {
                    heTongBeiAn.setPkid(id);
                    updateHeTongBeiAn(heTongBeiAn);
                }
            } catch (Exception e) {
                if (e instanceof MySQLIntegrityConstraintViolationException) {
                    //忽略主键冲突异常
                } else {
                    logger.warn(ExceptionUtils.getMessage(e));
                }
            }
        } else if (object instanceof JunGongBeiAn) {
            JunGongBeiAn junGongBeiAn = (JunGongBeiAn) object;
            String pkid = junGongBeiAn.getPkid();
            //String md5 = junGongBeiAn.getMd5();
            try {
                String id = mohurdService.selectJunGongBeiAn(junGongBeiAn);
                if (StringUtils.isBlank(id)) {
                    int result = mohurdService.insertJunGongBeiAn(junGongBeiAn);//新增
                    if (result > 0) {
                        logger.info(String.format("新增竣工验收备案 %s", pkid));
                    }
                } else {
                    junGongBeiAn.setPkid(id);
                    updateJunGongBeiAn(junGongBeiAn);
                }
            } catch (Exception e) {
                if (e instanceof MySQLIntegrityConstraintViolationException) {
                    //忽略主键冲突异常
                } else {
                    logger.warn(ExceptionUtils.getMessage(e));
                }
            }
        }
    }

    private void updateCompanyToUrl(Company company) {
        try {
            // 记录变更
            Company old = mohurdService.selectCompanyByUrl(company.getUrl());
            if (null != old) {
                //注册资本不记录变更--张夏晖2019-05-16
                company.setRegis_capital(old.getRegis_capital());
                List<FieldChange> changes = BeanUtils.compare(company, old);
                if (!changes.isEmpty()) {
                    for (FieldChange change : changes) {
                        mohurdService.insertFieldChangeRecord(change);
                    }
                }
            }
            int result = mohurdService.updateCompany(company,old);// 更新
            if (result > 0) {
                logger.info(String.format("更新企业基本信息【%s】【%s】", company.getCom_id(),company.getCom_name()));
            }
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getMessage(e));
        }
    }

    private void updateCompanyQual(CompanyQualification qualification) {
        try {
            // 记录变更
            CompanyQualification old = mohurdService.selectCompanyQualById(qualification.getPkid());
            if (null != old) {
                List<FieldChange> changes = BeanUtils.compare(qualification, old);
                if (!changes.isEmpty()) {
                    for (FieldChange change : changes) {
                        if(change.getColumn_name()!=null&&!"range".equals(change.getColumn_name())){
                            mohurdService.insertFieldChangeRecord(change);
                        }
                    }
                }
            }
            int result = mohurdService.updateCompanyQualification(qualification);// 更新
            if (result > 0) {
                logger.info(String.format("更新企业资质 %s", qualification.getPkid()));
            }
            //洗资质
            aptitudeCleanService.splitCompanyAptitudeByCompanyId(qualification.getCom_id());
            aptitudeCleanService.updateCompanyAptitude(qualification.getCom_id());
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getMessage(e));
        }
    }

    private void updateProject(Project project) {
        try {
            //记录变更
            Project old = mohurdService.selectProjectById(project.getPro_id());
            if (null != old) {
                List<FieldChange> changes = BeanUtils.compare(project, old);
                if (!changes.isEmpty()) {
                    for (FieldChange change : changes) {
                        mohurdService.insertFieldChangeRecord(change);
                    }
                }
            }
            int result = mohurdService.updateProject(project);// 更新
            if (result > 0) {
                logger.info(String.format("更新项目 %s", project.getPro_id()));
            }
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getMessage(e));
        }
    }

    public void updateProjectCompany(ProjectCompany projectCompany) {
        try {
            int result = mohurdService.updateProjectCompany(projectCompany);// 更新
            if (result > 0) {
                logger.info(String.format("更新项目企业关系 %s", projectCompany.getPkid()));
            }
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getMessage(e));
        }
    }

    private void updatePerson(Person person) {
        try {
            String table = person.getTable();
            //记录变更
            Person old = mohurdService.selectPersonById(person);
            if (null != old) {
                person.setTable("tb_person_" + person.getTable());
                List<FieldChange> changes = BeanUtils.compare(person, old);
                if (!changes.isEmpty()) {
                    for (FieldChange change : changes) {
                        if(change.getColumn_name()!=null&&!"valid_date".equals(change.getColumn_name())){
                            mohurdService.insertFieldChangeRecord(change);
                        }
                    }
                }
            }
            person.setTable(table);
            int result = mohurdService.updatePerson(person);// 更新
            if (result > 0) {
                logger.info(String.format("更新人员 %s", person.getPkid()));
            }
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getMessage(e));
        }
    }

    public void updatePersonChange(PersonChange change) {
        try {
            int result = mohurdService.updatePersonChange(change);// 更新
            if (result > 0) {
                logger.info(String.format("更新人员变更 %s", change.getPkid()));
            }
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getMessage(e));
        }
    }

    private void updatePersonProject(PersonProject personProject) {
        try {
            int result = mohurdService.updatePersonProject(personProject);// 更新
            if (result > 0) {
                logger.info(String.format("更新人员项目关系 %s", personProject.getPkid()));
            }
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getMessage(e));
        }
    }

    private void updateZhaoTouBiao(ZhaoTouBiao zhaoTouBiao) {
        try {
            //记录变更
            ZhaoTouBiao old = mohurdService.selectZhaoTouBiaoById(zhaoTouBiao.getPkid());
            if (null != old) {
                List<FieldChange> changes = BeanUtils.compare(zhaoTouBiao, old);
                if (!changes.isEmpty()) {
                    for (FieldChange change : changes) {
                        mohurdService.insertFieldChangeRecord(change);
                    }
                }
            }
            int result = mohurdService.updateZhaoTouBiao(zhaoTouBiao);// 更新
            if (result > 0) {
                logger.info(String.format("更新招投标 %s", zhaoTouBiao.getPkid()));
            }
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getMessage(e));
        }
    }

    private void updateShiGongTuShenCha(ShiGongTuShenCha shiGongTuShenCha) {
        try {
            //记录变更
            ShiGongTuShenCha old = mohurdService.selectShiGongTuShenChaById(shiGongTuShenCha.getPkid());
            if (null != old) {
                List<FieldChange> changes = BeanUtils.compare(shiGongTuShenCha, old);
                if (!changes.isEmpty()) {
                    for (FieldChange change : changes) {
                        mohurdService.insertFieldChangeRecord(change);
                    }
                }
            }
            int result = mohurdService.updateShiGongTuShenCha(shiGongTuShenCha);// 更新
            if (result > 0) {
                logger.info(String.format("更新施工图审查 %s", shiGongTuShenCha.getPkid()));
            }
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getMessage(e));
        }
    }

    private void updateShiGongXuKe(ShiGongXuKe shiGongXuKe) {
        try {
            //记录变更
            ShiGongXuKe old = mohurdService.selectShiGongXuKeById(shiGongXuKe.getPkid());
            if (null != old) {
                List<FieldChange> changes = BeanUtils.compare(shiGongXuKe, old);
                if (!changes.isEmpty()) {
                    for (FieldChange change : changes) {
                        mohurdService.insertFieldChangeRecord(change);
                    }
                }
            }
            int result = mohurdService.updateShiGongXuKe(shiGongXuKe);// 更新
            if (result > 0) {
                logger.info(String.format("更新施工许可 %s", shiGongXuKe.getPkid()));
            }
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getMessage(e));
        }
    }

    private void updateHeTongBeiAn(HeTongBeiAn heTongBeiAn) {
        try {
            //记录变更
            HeTongBeiAn old = mohurdService.selectHeTongBeiAnById(heTongBeiAn.getPkid());
            if (null != old) {
                List<FieldChange> changes = BeanUtils.compare(heTongBeiAn, old);
                if (!changes.isEmpty()) {
                    for (FieldChange change : changes) {
                        mohurdService.insertFieldChangeRecord(change);
                    }
                }
            }
            int result = mohurdService.updateHeTongBeiAn(heTongBeiAn);// 更新
            if (result > 0) {
                logger.info(String.format("更新合同备案 %s", heTongBeiAn.getPkid()));
            }
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getMessage(e));
        }
    }

    private void updateJunGongBeiAn(JunGongBeiAn junGongBeiAn) {
        try {
            //记录变更
            JunGongBeiAn old = mohurdService.selectJunGongBeiAnById(junGongBeiAn.getPkid());
            if (null != old) {
                List<FieldChange> changes = BeanUtils.compare(junGongBeiAn, old);
                if (!changes.isEmpty()) {
                    for (FieldChange change : changes) {
                        mohurdService.insertFieldChangeRecord(change);
                    }
                }
            }
            int result = mohurdService.updateJunGongBeiAn(junGongBeiAn);// 更新
            if (result > 0) {
                logger.info(String.format("更新竣工验收备案 %s", junGongBeiAn.getPkid()));
            }
        } catch (Exception e) {
            logger.warn(ExceptionUtils.getMessage(e));
        }
    }
}
