package com.silita.dao;

import com.silita.model.SkyProjJgChongq;

public interface SkyProjJgChongqMapper {

    /**
     * 查询竣工备案业绩是否存在
     * @param skyProjJgChongq
     * @return
     */
    String queryProjectJg(SkyProjJgChongq skyProjJgChongq);

    /**
     * 添加竣工备案
     * @param skyProjJgChongq
     * @return
     */
    int insertProjectJg(SkyProjJgChongq skyProjJgChongq);

    /**
     * 修改竣工备案
     * @param skyProjJgChongq
     * @return
     */
    int updateProjectJg(SkyProjJgChongq skyProjJgChongq);
}