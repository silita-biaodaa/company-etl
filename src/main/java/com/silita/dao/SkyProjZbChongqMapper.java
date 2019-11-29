package com.silita.dao;

import com.silita.model.SkyProjZbChongq;

/**
 * 重庆四库一中标业绩 sky_proj_zb_chongq
 */
public interface SkyProjZbChongqMapper  {

    /**
     * 添加中标业绩
     * @param skyProjZbChongq
     * @return
     */
    int insertProjectZhongbiao(SkyProjZbChongq skyProjZbChongq);

    /**
     * 删除企业的中标业绩
     * @param comId
     * @return
     */
    int deleteProjectZhongbiao(String comId);
}