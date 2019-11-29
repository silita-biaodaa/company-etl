package com.silita.dao;

import com.silita.model.TbCompanyPunish;

/**
 * 企业行政处罚 tb_company_punish
 */
public interface TbCompanyPunishMapper {

    /**
     * 根据企业id删除行政处罚
     * @param comId
     * @return
     */
    int deleteCompanyPunish(String comId);

    /**
     * 添加
     * @param companyPunish
     * @return
     */
    int insertCompanyPunish(TbCompanyPunish companyPunish);
}