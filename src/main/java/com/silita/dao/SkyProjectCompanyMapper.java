package com.silita.dao;

import com.silita.model.SkyProjectCompany;

public interface SkyProjectCompanyMapper {

    /**
     *  查询项目企业关联是否存在
     * @param skyProjectCompany
     * @return
     */
    String queryProjectCompanyExist(SkyProjectCompany skyProjectCompany);

    /**
     * 添加
     * @param skyProjectCompany
     * @return
     */
    int insertProjectCompany(SkyProjectCompany skyProjectCompany);

    /**
     * 修改
     * @param skyProjectCompany
     * @return
     */
    int updateProjectCompany(SkyProjectCompany skyProjectCompany);
}