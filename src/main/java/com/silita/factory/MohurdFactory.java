package com.silita.factory;

import com.silita.common.Constant;
import com.silita.common.redis.RedisUtils;
import com.silita.model.*;
import com.silita.service.MohurdService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    private MohurdService mohurdService;

    @Override
    public void process(Object object) {
        if (object instanceof Company) {
            Company company = (Company) object;
            String com_id = company.getCom_id();
            String md5 = company.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_Company, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_Company, com_id);
                if (!exists) {//主键md5不存在，新增
                    String id = mohurdService.selectCompany(company);
                    if (StringUtils.isBlank(id)) {
                        mohurdService.insertCompany(company);//新增
                    }
                    redisUtils.hset(Constant.Cache_Company, com_id, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    mohurdService.updateCompany(company);// 更新
                }
                redisUtils.hset(Constant.Cache_Company, md5, "");
            }
        } else if (object instanceof CompanyQualification) {
            CompanyQualification qualification = (CompanyQualification) object;
            String pkid = qualification.getPkid();
            String md5 = qualification.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_CompanyQual, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_CompanyQual, pkid);
                if (!exists) {//主键md5不存在，新增
                    String id = mohurdService.selectCompanyQualification(qualification);
                    if (StringUtils.isBlank(id)) {
                        mohurdService.insertCompanyQualification(qualification);//新增
                    }
                    redisUtils.hset(Constant.Cache_CompanyQual, pkid, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    mohurdService.updateCompanyQualification(qualification);// 更新
                }
                redisUtils.hset(Constant.Cache_CompanyQual, md5, "");
            }
        } else if (object instanceof Project) {
            Project project = (Project) object;
            String pkid = project.getPro_id();
            String md5 = project.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_Project, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_Project, pkid);
                if (!exists) {//主键md5不存在，新增
                    String id = mohurdService.selectProject(project);
                    if (StringUtils.isBlank(id)) {
                        mohurdService.insertProject(project);//新增
                    }
                    redisUtils.hset(Constant.Cache_Project, pkid, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    mohurdService.updateProject(project);// 更新
                }
                redisUtils.hset(Constant.Cache_Project, md5, "");
            }
        } else if (object instanceof Person) {
            Person person = (Person) object;
            String pkid = person.getPkid();
            String md5 = person.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_Person, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_Person, pkid);
                if (!exists) {//主键md5不存在，新增
                    String id = mohurdService.selectPerson(person);
                    if (StringUtils.isBlank(id)) {
                        mohurdService.insertPerson(person);//新增
                    }
                    redisUtils.hset(Constant.Cache_Person, pkid, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    mohurdService.updatePerson(person);// 更新
                }
                redisUtils.hset(Constant.Cache_Person, md5, "");
            }
        } else if (object instanceof PersonChange) {
            PersonChange change = (PersonChange) object;
            String pkid = change.getPkid();
            String md5 = change.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_PersonChange, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_PersonChange, pkid);
                if (!exists) {//主键md5不存在，新增
                    String id = mohurdService.selectPersonChange(change);
                    if (StringUtils.isBlank(id)) {
                        mohurdService.insertPersonChange(change);//新增
                    }
                    redisUtils.hset(Constant.Cache_PersonChange, pkid, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    mohurdService.updatePersonChange(change);// 更新
                }
                redisUtils.hset(Constant.Cache_PersonChange, md5, "");
            }
        } else if (object instanceof PersonProject) {
            PersonProject personProject = (PersonProject) object;
            String pkid = personProject.getPkid();
            String md5 = personProject.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_PersonProject, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_PersonProject, pkid);
                if (!exists) {//主键md5不存在，新增
                    String id = mohurdService.selectPersonProject(personProject);
                    if (StringUtils.isBlank(id)) {
                        mohurdService.insertPersonProject(personProject);//新增
                    }
                    redisUtils.hset(Constant.Cache_PersonProject, pkid, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    mohurdService.updatePersonProject(personProject);// 更新
                }
                redisUtils.hset(Constant.Cache_PersonProject, md5, "");
            }
        } else if (object instanceof ProjectCompany) {
            ProjectCompany projectCompany = (ProjectCompany) object;
            String pkid = projectCompany.getPkid();
            String md5 = projectCompany.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_ProjectCompany, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_ProjectCompany, pkid);
                if (!exists) {//主键md5不存在，新增
                    String id = mohurdService.selectProjectCompany(projectCompany);
                    if (StringUtils.isBlank(id)) {
                        mohurdService.insertProjectCompany(projectCompany);//新增
                    }
                    redisUtils.hset(Constant.Cache_ProjectCompany, pkid, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    mohurdService.updateProjectCompany(projectCompany);// 更新
                }
                redisUtils.hset(Constant.Cache_ProjectCompany, md5, "");
            }
        } else if (object instanceof ZhaoTouBiao) {
            ZhaoTouBiao zhaoTouBiao = (ZhaoTouBiao) object;
            String pkid = zhaoTouBiao.getPkid();
            String md5 = zhaoTouBiao.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_ZhaoTouBiao, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_ZhaoTouBiao, pkid);
                if (!exists) {//主键md5不存在，新增
                    String id = mohurdService.selectZhaoTouBiao(zhaoTouBiao);
                    if (StringUtils.isBlank(id)) {
                        mohurdService.insertZhaoTouBiao(zhaoTouBiao);//新增
                    }
                    redisUtils.hset(Constant.Cache_ZhaoTouBiao, pkid, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    mohurdService.updateZhaoTouBiao(zhaoTouBiao);// 更新
                }
                redisUtils.hset(Constant.Cache_ZhaoTouBiao, md5, "");
            }
        } else if (object instanceof ShiGongTuShenCha) {
            ShiGongTuShenCha shiGongTuShenCha = (ShiGongTuShenCha) object;
            String pkid = shiGongTuShenCha.getPkid();
            String md5 = shiGongTuShenCha.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_ShiGongTuShenCha, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_ShiGongTuShenCha, pkid);
                if (!exists) {//主键md5不存在，新增
                    String id = mohurdService.selectShiGongTuShenCha(shiGongTuShenCha);
                    if (StringUtils.isBlank(id)) {
                        mohurdService.insertShiGongTuShenCha(shiGongTuShenCha);//新增
                    }
                    redisUtils.hset(Constant.Cache_ShiGongTuShenCha, pkid, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    mohurdService.updateShiGongTuShenCha(shiGongTuShenCha);// 更新
                }
                redisUtils.hset(Constant.Cache_ShiGongTuShenCha, md5, "");
            }
        } else if (object instanceof ShiGongXuKe) {
            ShiGongXuKe shiGongXuKe = (ShiGongXuKe) object;
            String pkid = shiGongXuKe.getPkid();
            String md5 = shiGongXuKe.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_ShiGongXuKe, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_ShiGongXuKe, pkid);
                if (!exists) {//主键md5不存在，新增
                    String id = mohurdService.selectShiGongXuKe(shiGongXuKe);
                    if (StringUtils.isBlank(id)) {
                        mohurdService.insertShiGongXuKe(shiGongXuKe);//新增
                    }
                    redisUtils.hset(Constant.Cache_ShiGongXuKe, pkid, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    mohurdService.updateShiGongXuKe(shiGongXuKe);// 更新
                }
                redisUtils.hset(Constant.Cache_ShiGongXuKe, md5, "");
            }
        } else if (object instanceof HeTongBeiAn) {
            HeTongBeiAn heTongBeiAn = (HeTongBeiAn) object;
            String pkid = heTongBeiAn.getPkid();
            String md5 = heTongBeiAn.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_HeTongBeiAn, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_HeTongBeiAn, pkid);
                if (!exists) {//主键md5不存在，新增
                    String id = mohurdService.selectHeTongBeiAn(heTongBeiAn);
                    if (StringUtils.isBlank(id)) {
                        mohurdService.insertHeTongBeiAn(heTongBeiAn);//新增
                    }
                    redisUtils.hset(Constant.Cache_HeTongBeiAn, pkid, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    mohurdService.updateHeTongBeiAn(heTongBeiAn);// 更新
                }
                redisUtils.hset(Constant.Cache_HeTongBeiAn, md5, "");
            }
        } else if (object instanceof JunGongBeiAn) {
            JunGongBeiAn junGongBeiAn = (JunGongBeiAn) object;
            String pkid = junGongBeiAn.getPkid();
            String md5 = junGongBeiAn.getMd5();
            boolean exists = redisUtils.hexists(Constant.Cache_JunGongBeiAn, md5);
            if (!exists) {//实体MD5不存在
                exists = redisUtils.hexists(Constant.Cache_JunGongBeiAn, pkid);
                if (!exists) {//主键md5不存在，新增
                    String id = mohurdService.selectJunGongBeiAn(junGongBeiAn);
                    if (StringUtils.isBlank(id)) {
                        mohurdService.insertJunGongBeiAn(junGongBeiAn);//新增
                    }
                    redisUtils.hset(Constant.Cache_JunGongBeiAn, pkid, "");
                } else {//主键MD5存在，实体MD5不存在，则说明是更新操作
                    mohurdService.updateJunGongBeiAn(junGongBeiAn);// 更新
                }
                redisUtils.hset(Constant.Cache_JunGongBeiAn, md5, "");
            }
        }
    }
}
