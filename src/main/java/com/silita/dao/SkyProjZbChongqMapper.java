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
     * 查询中标是否存在
     * @param skyProjZbChongq
     * @return
     */
    String queryProjectZhongbiao(SkyProjZbChongq skyProjZbChongq);

    /**
     * 修改中标业绩
     * @param skyProjZbChongq
     * @return
     */
    int updateProjectZhongbiao(SkyProjZbChongq skyProjZbChongq);
}