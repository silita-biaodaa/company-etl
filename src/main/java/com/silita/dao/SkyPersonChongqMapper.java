package com.silita.dao;

import com.silita.model.SkyPersonChongq;

public interface SkyPersonChongqMapper {

    /**
     * 查询是否存在
     * @param personChongq
     * @return
     */
    String queryPersonExits(SkyPersonChongq personChongq);

    /**
     * 添加记录
     * @param personChongq
     * @return
     */
    int insertPerson(SkyPersonChongq personChongq);

    /**
     * 删除企业人员
     * @param comId
     * @return
     */
    int batchDeletePerson(String comId);
}