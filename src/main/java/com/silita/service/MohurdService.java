package com.silita.service;

import com.silita.dao.MohurdMapper;
import com.silita.model.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Author:chenzhiqiang
 * @Date:2018/5/4 16:51
 * @Description: 全国四库一平台业务类
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
public class MohurdService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MohurdMapper mohurdMapper;

    /**
     * 查询企业
     * 条件中不带公司名称查
     *
     * @param company
     * @return 企业id
     */
    public String selectCompany(Company company) {
        String id = mohurdMapper.selectCompanyByName(company.getCom_name());//先通过公司名称查
        if (StringUtils.isBlank(id)) {
            id = mohurdMapper.selectCompany(company);//如果企业名称未查到再通过三证查
        }
        return id;
    }

    /**
     * 查询企业
     * 条件中必须带公司名称查
     *
     * @param companyName
     * @return 企业id
     */
    public String selectCompanyByName(String companyName) {
        return mohurdMapper.selectCompanyByName(companyName);
    }

    /**
     * 通过id查询企业
     *
     * @param id
     * @return
     */
    public Company selectCompanyById(String id) {
        return mohurdMapper.selectCompanyById(id);
    }

    /**
     * 插入企业
     *
     * @param company
     * @return
     */
    public void insertCompany(Company company) {
        mohurdMapper.insertCompany(company);
    }

    /**
     * 更新企业
     *
     * @param company
     */
    public void updateCompany(Company company) {
        if (StringUtils.isNotBlank(company.getType())) {//造价企业
            Company com = mohurdMapper.selectCompanyById(company.getCom_id());
            if (StringUtils.isBlank(com.getBusiness_num())) {
                com.setBusiness_num(company.getBusiness_num());
            }
            if (StringUtils.isBlank(com.getCredit_code())) {
                com.setCredit_code(company.getCredit_code());
            }
            if (StringUtils.isBlank(com.getOrg_code())) {
                com.setOrg_code(company.getOrg_code());
            }
            mohurdMapper.updateCompany(com);
        } else {//非造价企业
            mohurdMapper.updateCompany(company);
        }
    }

    /**
     * 插入企业资质
     *
     * @param qualification
     */
    public void insertCompanyQualification(CompanyQualification qualification) {
        mohurdMapper.insertCompanyQualification(qualification);
    }

    /**
     * 通过id查询企业资质
     *
     * @param id
     * @return
     */
    public CompanyQualification selectCompanyQualById(String id) {
        return mohurdMapper.selectCompanyQualById(id);
    }

    /**
     * 查询企业资质
     *
     * @param qualification
     * @return
     */
    public String selectCompanyQualification(CompanyQualification qualification) {
        return mohurdMapper.selectCompanyQualification(qualification);
    }

    /**
     * 更新企业资质
     *
     * @param companyQualification
     */
    public void updateCompanyQualification(CompanyQualification companyQualification) {
        if (StringUtils.isNotBlank(companyQualification.getPkid())) {
            mohurdMapper.updateCompanyQualification(companyQualification);
        }
    }

    /**
     * 通过id查询人员
     *
     * @param person
     * @return
     */
    public Person selectPersonById(Person person) {
        return mohurdMapper.selectPersonById(person);
    }

    /**
     * 查询人员基本信息
     *
     * @param person
     */
    public String selectPerson(Person person) {
        return mohurdMapper.selectPerson(person);
    }

    /**
     * 插入人员基本信息
     *
     * @param person
     */
    public String insertPerson(Person person) {
        mohurdMapper.insertPerson(person);
        return person.getPkid();
    }

    /**
     * 更新人员信息
     *
     * @param person
     */
    public void updatePerson(Person person) {
        if (StringUtils.isNotBlank(person.getPkid())) {
            mohurdMapper.updatePerson(person);
        }
    }

    /**
     * 查询人员变更记录
     *
     * @param personChange
     * @return
     */
    public String selectPersonChange(PersonChange personChange) {
        return mohurdMapper.selectPersonChange(personChange);
    }

    /**
     * 插入人员变更记录
     *
     * @param personChange
     */
    public void insertPersonChange(PersonChange personChange) {
        mohurdMapper.insertPersonChange(personChange);
    }

    /**
     * 更新人员变更记录
     *
     * @param personChange
     */
    public void updatePersonChange(PersonChange personChange) {
        if (StringUtils.isNotBlank(personChange.getPkid())) {
            mohurdMapper.updatePersonChange(personChange);
        }
    }

    /**
     * 查询项目基本信息
     *
     * @param project
     * @return
     */
    public String selectProject(Project project) {
        return mohurdMapper.selectProject(project);
    }

    /**
     * 通过id查询项目
     *
     * @param id
     * @return
     */
    public Project selectProjectById(String id) {
        return mohurdMapper.selectProjectById(id);
    }

    /**
     * 插入项目基本信息
     *
     * @param project
     */
    public void insertProject(Project project) {
        mohurdMapper.insertProject(project);
    }

    /**
     * 更新项目基本信息
     *
     * @param project
     */
    public void updateProject(Project project) {
        if (StringUtils.isNotBlank(project.getPro_id())) {
            mohurdMapper.updateProject(project);
        }
    }

    /**
     * 查询招投标
     *
     * @param zhaoTouBiao
     * @return
     */
    public String selectZhaoTouBiao(ZhaoTouBiao zhaoTouBiao) {
        return mohurdMapper.selectZhaoTouBiao(zhaoTouBiao);
    }

    /**
     * 通过id查询招投标
     *
     * @param id
     * @return
     */
    public ZhaoTouBiao selectZhaoTouBiaoById(String id) {
        return mohurdMapper.selectZhaoTouBiaoById(id);
    }

    /**
     * 插入招投标
     *
     * @param zhaoTouBiao
     */
    public void insertZhaoTouBiao(ZhaoTouBiao zhaoTouBiao) {
        mohurdMapper.insertZhaoTouBiao(zhaoTouBiao);
    }

    /**
     * 更新招投标
     *
     * @param zhaoTouBiao
     */
    public void updateZhaoTouBiao(ZhaoTouBiao zhaoTouBiao) {
        if (StringUtils.isNotBlank(zhaoTouBiao.getPkid())) {
            mohurdMapper.updateZhaoTouBiao(zhaoTouBiao);
        }
    }

    /**
     * 查询人员和项目的关系
     *
     * @param personProject
     */
    public String selectPersonProject(PersonProject personProject) {
        return mohurdMapper.selectPersonProject(personProject);
    }

    /**
     * 插入人员和项目的关系
     *
     * @param personProject
     */
    public void insertPersonProject(PersonProject personProject) {
        mohurdMapper.insertPersonProject(personProject);
    }

    /**
     * 更新人员和项目的关系
     *
     * @param personProject
     */
    public void updatePersonProject(PersonProject personProject) {
        if (StringUtils.isNotBlank(personProject.getPkid())) {
            mohurdMapper.updatePersonProject(personProject);
        }
    }

    /**
     * 查询施工图审查
     *
     * @param shenCha
     * @return
     */
    public String selectShiGongTuShenCha(ShiGongTuShenCha shenCha) {
        return mohurdMapper.selectShiGongTuShenCha(shenCha);
    }

    /**
     * 通过id查询施工图审查
     *
     * @param id
     * @return
     */
    public ShiGongTuShenCha selectShiGongTuShenChaById(String id) {
        return mohurdMapper.selectShiGongTuShenChaById(id);
    }

    /**
     * 插入施工图审查
     *
     * @param shencha
     */
    public void insertShiGongTuShenCha(ShiGongTuShenCha shencha) {
        mohurdMapper.insertShiGongTuShenCha(shencha);
    }

    /**
     * 更新施工图审查
     *
     * @param shencha
     */
    public void updateShiGongTuShenCha(ShiGongTuShenCha shencha) {
        if (StringUtils.isNotBlank(shencha.getPkid())) {
            mohurdMapper.updateShiGongTuShenCha(shencha);
        }
    }

    /**
     * 查询项目和公司的关系
     *
     * @param projectCompany
     * @return
     */
    public String selectProjectCompany(ProjectCompany projectCompany) {
        String innerId = projectCompany.getInnerId();
        String companyName = projectCompany.getCom_name();
        if (StringUtils.isNotBlank(innerId)) {
            // 如果innerId不为空，就把公司名清空，这种做法是是避免公司名变了导致存多条数据
            projectCompany.setCom_name(null);
        }
        String id = mohurdMapper.selectProjectCompany(projectCompany);
        //查询之前去掉了，查询之后要补上
        projectCompany.setCom_name(companyName);
        return id;
    }

    /**
     * 插入项目和公司的关系
     *
     * @param projectCompany
     */
    public void insertProjectCompany(ProjectCompany projectCompany) {
        if (StringUtils.isNotBlank(projectCompany.getCom_name())) {
            mohurdMapper.insertProjectCompany(projectCompany);
        }
    }

    /**
     * 更新项目和公司的关系
     *
     * @param projectCompany
     */
    public void updateProjectCompany(ProjectCompany projectCompany) {
        if (StringUtils.isNotBlank(projectCompany.getCom_name()) && StringUtils.isNotBlank(projectCompany.getPro_id())) {
            mohurdMapper.updateProjectCompany(projectCompany);
        }
    }

    /**
     * 查询施工许可
     *
     * @param shiGongXuKe
     * @return
     */
    public String selectShiGongXuKe(ShiGongXuKe shiGongXuKe) {
        return mohurdMapper.selectShiGongXuKe(shiGongXuKe);
    }

    /**
     * 通过id查施工许可
     *
     * @param id
     * @return
     */
    public ShiGongXuKe selectShiGongXuKeById(String id) {
        return mohurdMapper.selectShiGongXuKeById(id);
    }

    /**
     * 新增施工许可
     *
     * @param shiGongXuKe
     */
    public void insertShiGongXuKe(ShiGongXuKe shiGongXuKe) {
        mohurdMapper.insertShiGongXuKe(shiGongXuKe);
    }

    /**
     * 更新施工许可
     *
     * @param shiGongXuKe
     */
    public void updateShiGongXuKe(ShiGongXuKe shiGongXuKe) {
        if (StringUtils.isNotBlank(shiGongXuKe.getPkid())) {
            mohurdMapper.updateShiGongXuKe(shiGongXuKe);
        }
    }

    /**
     * 查询合同备案
     *
     * @param heTongBeiAn
     * @return
     */
    public String selectHeTongBeiAn(HeTongBeiAn heTongBeiAn) {
        return mohurdMapper.selectHeTongBeiAn(heTongBeiAn);
    }

    /**
     * 通过id查询合同备案
     *
     * @param id
     * @return
     */
    public HeTongBeiAn selectHeTongBeiAnById(String id) {
        return mohurdMapper.selectHeTongBeiAnById(id);
    }

    /**
     * 新增合同备案
     *
     * @param heTongBeiAn
     */
    public void insertHeTongBeiAn(HeTongBeiAn heTongBeiAn) {
        mohurdMapper.insertHeTongBeiAn(heTongBeiAn);
    }

    /**
     * 更新合同备案
     *
     * @param heTongBeiAn
     */
    public void updateHeTongBeiAn(HeTongBeiAn heTongBeiAn) {
        if (StringUtils.isNotBlank(heTongBeiAn.getPkid())) {
            mohurdMapper.updateHeTongBeiAn(heTongBeiAn);
        }
    }

    /**
     * 查询竣工验收备案
     *
     * @param junGongBeiAn
     * @return
     */
    public String selectJunGongBeiAn(JunGongBeiAn junGongBeiAn) {
        return mohurdMapper.selectJunGongBeiAn(junGongBeiAn);
    }

    /**
     * 通过id查询竣工验收备案
     *
     * @param id
     * @return
     */
    public JunGongBeiAn selectJunGongBeiAnById(String id) {
        return mohurdMapper.selectJunGongBeiAnById(id);
    }

    /**
     * 新增竣工验收备案
     *
     * @param junGongBeiAn
     */
    public void insertJunGongBeiAn(JunGongBeiAn junGongBeiAn) {
        mohurdMapper.insertJunGongBeiAn(junGongBeiAn);
    }

    /**
     * 更新竣工验收备案
     *
     * @param junGongBeiAn
     */
    public void updateJunGongBeiAn(JunGongBeiAn junGongBeiAn) {
        if (StringUtils.isNotBlank(junGongBeiAn.getPkid())) {
            mohurdMapper.updateJunGongBeiAn(junGongBeiAn);
        }
    }

    /**
     * 查出未抓到资质的公司重新放回抓取队列
     * ps:有的公司，没有资质
     *
     * @return
     */
    public List<Map<String, Object>> selectNoQualificationCompany() {
        return mohurdMapper.selectNoQualificationCompany();
    }

    /**
     * 插入字段变更记录
     *
     * @param change
     */
    public void insertFieldChangeRecord(FieldChange change) {
        mohurdMapper.insertFieldChangeRecord(change);
    }
}
