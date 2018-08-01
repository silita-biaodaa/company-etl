package com.silita.dao;


import com.silita.model.Person;

import java.util.List;

/**
 * 删除既有非造价企业又有造价企业的所有记录
 */
public interface DeleteCostCompanyMapper {
    /**
     * 查询出企业名称大于1的所有企业中的1个
     *
     * @return
     */
    public List selectAllCompanyByName();

    /**
     * 查询出组织机构代码大于1的所有企业中的1个
     *
     * @return
     */
    public List selectAllCompanyByCreditCode();

    /**
     * 查询出组织机构代码大于1的所有企业中的1个
     *
     * @return
     */
    public List selectAllCompanyByOrgCode();

    /**
     * 查询出营业执照编号大于1的所有企业中的1个
     *
     * @return
     */
    public List selectAllCompanyByBussNum();

    /**
     * 通过公司名称查询出所有该名称的企业
     *
     * @return
     */
    public List selectCompany_Cost(String com_name);

    /**
     * 通过企业id删除公司
     *
     * @param com_id
     */
    public void deleteCompanyById(String com_id);

    /**
     * 通过企业id查询所有资质
     *
     * @param com_id
     * @return
     */
    public List selectCompanyQual_Cost(String com_id);

    /**
     * 通过id删除资质
     *
     * @param pkid
     */
    public void deleteCompanyQualById(String pkid);

    /**
     * 通过com_id查询所有人员
     *
     * @param person
     * @return
     */
    public List selectPerson__Cost(Person person);

    /**
     * 删除人员信息
     *
     * @param person
     */
    public void deletePersonById(Person person);
}