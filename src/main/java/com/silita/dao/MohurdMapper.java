package com.silita.dao;

import com.silita.spider.common.model.*;

import java.util.List;
import java.util.Map;

/**
 * @Author:chenzhiqiang
 * @Date:2018/5/4 16:38
 * @Description: 全国四库一平台数据层接口
 */
public interface MohurdMapper {
    /**
     * 查询企业
     * 条件中不带公司名称查
     *
     * @param company
     * @return 企业id
     */
    public String selectCompany(Company company);

    /**
     * 查询企业
     * 条件中必须带公司名称查
     *
     * @param companyName
     * @return 企业id
     */
    public String selectCompanyByName(String companyName);

    /**
     * 通过id查询企业
     *
     * @param id
     * @return
     */
    public Company selectCompanyById(String id);

    /**
     * 插入企业
     *
     * @param company
     */
    public int insertCompany(Company company);

    /**
     * 更新企业
     *
     * @param company
     */
    public int updateCompany(Company company);

    /**
     * 插入企业资质
     *
     * @param qualification
     */
    public int insertCompanyQualification(CompanyQualification qualification);

    /**
     * 通过id查询企业资质
     *
     * @param id
     * @return
     */
    public CompanyQualification selectCompanyQualById(String id);

    /**
     * 查询企业资质
     *
     * @param qualification
     * @return 资质id
     */
    public String selectCompanyQualification(CompanyQualification qualification);

    /**
     * 更新企业资质
     *
     * @param companyQualification
     */
    public int updateCompanyQualification(CompanyQualification companyQualification);

    /**
     * 查询人员信息
     *
     * @param person
     * @return 人员id
     */
    public String selectPerson(Person person);

    /**
     * 通过id查询人员
     *
     * @param person
     * @return
     */
    public Person selectPersonById(Person person);

    /**
     * 插入人员信息
     *
     * @param person
     */
    public void insertPerson(Person person);

    /**
     * 更新人员信息
     *
     * @param person
     */
    public int updatePerson(Person person);

    /**
     * 查询人员变更记录
     *
     * @param personChange
     * @return
     */
    public String selectPersonChange(PersonChange personChange);

    /**
     * 插入人员变更记录
     *
     * @param personChange
     */
    public int insertPersonChange(PersonChange personChange);

    /**
     * 更新人员变更记录
     *
     * @param personChange
     */
    public int updatePersonChange(PersonChange personChange);

    /**
     * 查询项目基本信息
     *
     * @param project
     * @return
     */
    public String selectProject(Project project);

    /**
     * 通过id查询项目
     *
     * @param id
     * @return
     */
    public Project selectProjectById(String id);

    /**
     * 插入项目基本信息
     *
     * @param project
     */
    public int insertProject(Project project);

    /**
     * 更新项目基本信息
     *
     * @param project
     */
    public int updateProject(Project project);

    /**
     * 查询招投标
     *
     * @param zhaoTouBiao
     * @return
     */
    public String selectZhaoTouBiao(ZhaoTouBiao zhaoTouBiao);

    /**
     * 通过id查询招投标
     *
     * @param id
     * @return
     */
    public ZhaoTouBiao selectZhaoTouBiaoById(String id);

    /**
     * 插入招投标
     *
     * @param zhaoTouBiao
     */
    public int insertZhaoTouBiao(ZhaoTouBiao zhaoTouBiao);

    /**
     * 更新招投标
     *
     * @param zhaoTouBiao
     */
    public int updateZhaoTouBiao(ZhaoTouBiao zhaoTouBiao);

    /**
     * 查询人员和项目的关系
     *
     * @param personProject
     * @return id
     */
    public String selectPersonProject(PersonProject personProject);

    /**
     * 插入人员和项目的关系
     *
     * @param personProject
     */
    public int insertPersonProject(PersonProject personProject);

    /**
     * 更新人员和项目的关系
     *
     * @param personProject
     */
    public int updatePersonProject(PersonProject personProject);

    /**
     * 查询施工图审查
     *
     * @param shenCha
     * @return
     */
    public String selectShiGongTuShenCha(ShiGongTuShenCha shenCha);

    /**
     * 通过id查询施工图审查
     *
     * @param id
     * @return
     */
    public ShiGongTuShenCha selectShiGongTuShenChaById(String id);

    /**
     * 插入施工图审查
     *
     * @param shencha
     */
    public int insertShiGongTuShenCha(ShiGongTuShenCha shencha);

    /**
     * 更新施工图审查
     *
     * @param shencha
     */
    public int updateShiGongTuShenCha(ShiGongTuShenCha shencha);

    /**
     * 查询项目和公司的关系
     *
     * @param projectCompany
     * @return
     */
    public String selectProjectCompany(ProjectCompany projectCompany);

    /**
     * 插入项目和公司的关系
     *
     * @param projectCompany
     */
    public int insertProjectCompany(ProjectCompany projectCompany);

    /**
     * 更新项目和公司的关系
     *
     * @param projectCompany
     */
    public int updateProjectCompany(ProjectCompany projectCompany);

    /**
     * 查询施工许可
     *
     * @param shiGongXuKe
     * @return
     */
    public String selectShiGongXuKe(ShiGongXuKe shiGongXuKe);

    /**
     * 通过id查施工许可
     *
     * @param id
     * @return
     */
    public ShiGongXuKe selectShiGongXuKeById(String id);

    /**
     * 新增施工许可
     *
     * @param shiGongXuKe
     */
    public int insertShiGongXuKe(ShiGongXuKe shiGongXuKe);

    /**
     * 更新施工许可
     *
     * @param shiGongXuKe
     */
    public int updateShiGongXuKe(ShiGongXuKe shiGongXuKe);

    /**
     * 查询合同备案
     *
     * @param heTongBeiAn
     * @return
     */
    public String selectHeTongBeiAn(HeTongBeiAn heTongBeiAn);

    /**
     * 通过id查询合同备案
     *
     * @param id
     * @return
     */
    public HeTongBeiAn selectHeTongBeiAnById(String id);

    /**
     * 新增合同备案
     *
     * @param heTongBeiAn
     */
    public int insertHeTongBeiAn(HeTongBeiAn heTongBeiAn);

    /**
     * 更新合同备案
     *
     * @param heTongBeiAn
     */
    public int updateHeTongBeiAn(HeTongBeiAn heTongBeiAn);

    /**
     * 查询竣工验收备案
     *
     * @param junGongBeiAn
     * @return
     */
    public String selectJunGongBeiAn(JunGongBeiAn junGongBeiAn);

    /**
     * 通过id查询竣工验收备案
     *
     * @param id
     * @return
     */
    public JunGongBeiAn selectJunGongBeiAnById(String id);

    /**
     * 新增竣工验收备案
     *
     * @param junGongBeiAn
     */
    public int insertJunGongBeiAn(JunGongBeiAn junGongBeiAn);

    /**
     * 更新竣工验收备案
     *
     * @param junGongBeiAn
     */
    public int updateJunGongBeiAn(JunGongBeiAn junGongBeiAn);

    /**
     * 查出未抓到资质的公司重新放回抓取队列
     * ps:有的公司，没有资质
     *
     * @return
     */
    public List<Map<String, Object>> selectNoQualificationCompany();

    /**
     * 插入字段变更记录
     *
     * @param change
     */
    public int insertFieldChangeRecord(FieldChange change);
}
