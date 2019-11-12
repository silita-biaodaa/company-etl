package com.silita.dao;

import com.silita.model.TbCompanyQualification;
import com.silita.spider.common.model.CompanyQualification;

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

    /**
     * 查询资质是否存在
     * @param qualification
     * @return
     */
    String queryCompanyQualficationExist(TbCompanyQualification qualification);

    /**
     * 添加企业资质
     * @param companyQualification
     * @return
     */
    int inertCompanyQualfication(TbCompanyQualification companyQualification);

    /**
     * 修改企业资质更新时间
     * @param pkid
     * @return
     */
    int updateCompanyQualfication(String pkid);
}
