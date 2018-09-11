package com.silita.dao;


import com.silita.spider.common.model.Company;

public interface CompanyMapper{
    /**
     * 根据企业id更新企业资质
     * @param company
     */
    void updateCompanyRangeByComId(Company company);
}