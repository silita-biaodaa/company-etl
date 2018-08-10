package com.silita.service;

import com.silita.model.CompanyQualification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IAptitudeCleanService {

    /**
     * 拆分全部不标准的企业资质
     */
    void splitAllCompanyAptitude();

    /**
     * 更新全部标准企业资质到企业表
     */
    void updateAllCompanyAptitude();

    /**
     * 根据企业id拆分不标准的企业资质
     *
     * @param companyId
     */
    void splitCompanyAptitudeByCompanyId(String companyId);

    /**
     * 根据企业id更新标准企业资质到企业表
     *
     * @param companyId
     */
    void updateCompanyAptitude(String companyId);

    /**
     * 根据sql条件清洗资质
     *
     * @param params
     * @return
     */
    void cleanQualificationBySql(Map params);
}
