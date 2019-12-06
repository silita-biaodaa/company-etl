package com.silita.dao;

import com.silita.model.SkyProjSgChongq;

/**
 * 施工许可业绩
 */
public interface SkyProjSgChongqMapper {

    /**
     * 查询施工业绩是否存在
     * @param skyProjSgChongq
     * @return
     */
    String queryProjectSgExist(SkyProjSgChongq skyProjSgChongq);

    /**
     * 添加施工业绩
     * @param skyProjSgChongq
     * @return
     */
    int insertProjectSg(SkyProjSgChongq skyProjSgChongq);

    /**
     * 修改施工业绩
     * @param skyProjSgChongq
     * @return
     */
    int updateProjectSg(SkyProjSgChongq skyProjSgChongq);
}