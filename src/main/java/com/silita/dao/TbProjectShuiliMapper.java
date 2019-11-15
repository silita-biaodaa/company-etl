package com.silita.dao;

import com.silita.model.TbProjectShuili;

public interface TbProjectShuiliMapper {

    /**
     * 判断是否存在
     *
     * @return
     */
    Integer queryProjectExits(TbProjectShuili projectShuili);

    /**
     * 添加
     *
     * @return
     */
    int insertProjectTraffic(TbProjectShuili projectShuili);

    /**
     * 修改
     * @param projectShuili
     * @return
     */
    int updateProjectTraffic(TbProjectShuili projectShuili);

}