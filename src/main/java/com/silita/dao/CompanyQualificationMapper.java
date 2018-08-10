package com.silita.dao;

import com.silita.model.CompanyQualification;

import java.util.List;
import java.util.Map;

public interface CompanyQualificationMapper {
    /**
     * 根据资质类别获取资质个数
     *
     * @return
     */
    Integer getCompanyQualificationTotal();

    /**
     * 根据资质类别获取获取资质
     *
     * @param params
     * @return
     */
    List<CompanyQualification> listCompanyQualification(Map params);

    /**
     * 根据企业id获取企业资质证书
     *
     * @param companyId
     * @return
     */
    List<CompanyQualification> getCompanyQualificationByComId(String companyId);

    /**
     * 根据sql获取获取资质
     *
     * @param params
     * @return
     */
    List<CompanyQualification> getCompanyQualificationBySql(Map params);
}
