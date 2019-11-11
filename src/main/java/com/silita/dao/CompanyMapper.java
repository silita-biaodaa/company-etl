package com.silita.dao;


import com.silita.model.TbCompany;
import com.silita.spider.common.model.Company;

import java.util.Map;

public interface CompanyMapper{
    /**
     * 根据企业id更新企业资质
     * @param company
     */
    void updateCompanyRangeByComId(Company company);

    /**
     * 企业是否存在
     * @param param
     * @return
     */
    Map<String,Object> queryCompanyExist(Map<String,Object> param);

    /**
     * 添加企业
     * @param tbCompany
     * @return
     */
    int insertCompany(TbCompany tbCompany);

    /**
     * 修改企业来源
     * @param param
     * @return
     */
    int updateCompanyChannel(Map<String,Object> param);

    /**
     * 添加关联表数据
     * @param param
     * @return
     */
    int insertCompanyRel(Map<String,Object> param);

    /**
     * 修改关联表数据
     * @param param
     * @return
     */
    int updateCompanyRel(Map<String,Object> param);
}