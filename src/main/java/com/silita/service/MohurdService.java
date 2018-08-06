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
    public int insertCompany(Company company) {
        return mohurdMapper.insertCompany(company);
    }

    /**
     * 更新企业
     *
     * @param company
     */
    public int updateCompany(Company company) {
        int result = 0;
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
            result = mohurdMapper.updateCompany(com);
        } else {//非造价企业
            result = mohurdMapper.updateCompany(company);
        }
        return result;
    }

    /**
     * 插入企业资质
     *
     * @param qualification
     */
    public int insertCompanyQualification(CompanyQualification qualification) {
        return mohurdMapper.insertCompanyQualification(qualification);
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
    public int updateCompanyQualification(CompanyQualification companyQualification) {
        if (StringUtils.isNotBlank(companyQualification.getPkid())) {
            return mohurdMapper.updateCompanyQualification(companyQualification);
        }
        return 0;
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
    public int updatePerson(Person person) {
        if (StringUtils.isNotBlank(person.getPkid())) {
            return mohurdMapper.updatePerson(person);
        }
        return 0;
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
    public int insertPersonChange(PersonChange personChange) {
        return mohurdMapper.insertPersonChange(personChange);
    }

    /**
     * 更新人员变更记录
     *
     * @param personChange
     */
    public int updatePersonChange(PersonChange personChange) {
        if (StringUtils.isNotBlank(personChange.getPkid())) {
            return mohurdMapper.updatePersonChange(personChange);
        }
        return 0;
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
    public int insertProject(Project project) {
        return mohurdMapper.insertProject(project);
    }

    /**
     * 更新项目基本信息
     *
     * @param project
     */
    public int updateProject(Project project) {
        if (StringUtils.isNotBlank(project.getPro_id())) {
            return mohurdMapper.updateProject(project);
        }
        return 0;
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
    public int insertZhaoTouBiao(ZhaoTouBiao zhaoTouBiao) {
        return mohurdMapper.insertZhaoTouBiao(zhaoTouBiao);
    }

    /**
     * 更新招投标
     *
     * @param zhaoTouBiao
     */
    public int updateZhaoTouBiao(ZhaoTouBiao zhaoTouBiao) {
        if (StringUtils.isNotBlank(zhaoTouBiao.getPkid())) {
            return mohurdMapper.updateZhaoTouBiao(zhaoTouBiao);
        }
        return 0;
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
    public int insertPersonProject(PersonProject personProject) {
        return mohurdMapper.insertPersonProject(personProject);
    }

    /**
     * 更新人员和项目的关系
     *
     * @param personProject
     */
    public int updatePersonProject(PersonProject personProject) {
        if (StringUtils.isNotBlank(personProject.getPkid())) {
            return mohurdMapper.updatePersonProject(personProject);
        }
        return 0;
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
    public int insertShiGongTuShenCha(ShiGongTuShenCha shencha) {
        return mohurdMapper.insertShiGongTuShenCha(shencha);
    }

    /**
     * 更新施工图审查
     *
     * @param shencha
     */
    public int updateShiGongTuShenCha(ShiGongTuShenCha shencha) {
        if (StringUtils.isNotBlank(shencha.getPkid())) {
            return mohurdMapper.updateShiGongTuShenCha(shencha);
        }
        return 0;
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
    public int insertProjectCompany(ProjectCompany projectCompany) {
        if (StringUtils.isNotBlank(projectCompany.getCom_name())) {
            return mohurdMapper.insertProjectCompany(projectCompany);
        }
        return 0;
    }

    /**
     * 更新项目和公司的关系
     *
     * @param projectCompany
     */
    public int updateProjectCompany(ProjectCompany projectCompany) {
        if (StringUtils.isNotBlank(projectCompany.getCom_name()) && StringUtils.isNotBlank(projectCompany.getPro_id())) {
            return mohurdMapper.updateProjectCompany(projectCompany);
        }
        return 0;
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
    public int insertShiGongXuKe(ShiGongXuKe shiGongXuKe) {
        return mohurdMapper.insertShiGongXuKe(shiGongXuKe);
    }

    /**
     * 更新施工许可
     *
     * @param shiGongXuKe
     */
    public int updateShiGongXuKe(ShiGongXuKe shiGongXuKe) {
        if (StringUtils.isNotBlank(shiGongXuKe.getPkid())) {
            return mohurdMapper.updateShiGongXuKe(shiGongXuKe);
        }
        return 0;
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
    public int insertHeTongBeiAn(HeTongBeiAn heTongBeiAn) {
        return mohurdMapper.insertHeTongBeiAn(heTongBeiAn);
    }

    /**
     * 更新合同备案
     *
     * @param heTongBeiAn
     */
    public int updateHeTongBeiAn(HeTongBeiAn heTongBeiAn) {
        if (StringUtils.isNotBlank(heTongBeiAn.getPkid())) {
            return mohurdMapper.updateHeTongBeiAn(heTongBeiAn);
        }
        return 0;
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
    public int insertJunGongBeiAn(JunGongBeiAn junGongBeiAn) {
        return mohurdMapper.insertJunGongBeiAn(junGongBeiAn);
    }

    /**
     * 更新竣工验收备案
     *
     * @param junGongBeiAn
     */
    public int updateJunGongBeiAn(JunGongBeiAn junGongBeiAn) {
        if (StringUtils.isNotBlank(junGongBeiAn.getPkid())) {
            return mohurdMapper.updateJunGongBeiAn(junGongBeiAn);
        }
        return 0;
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
    public int insertFieldChangeRecord(FieldChange change) {
        return mohurdMapper.insertFieldChangeRecord(change);
    }
}
