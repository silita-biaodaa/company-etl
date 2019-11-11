package com.silita.dao;

import com.silita.model.TbProjectTraffic;
import com.silita.model.TbTrafficPerson;

import java.util.List;

public interface TbProjectTrafficMapper {

    /**
     * 判断是否存在
     *
     * @param projectTraffic
     * @return
     */
    Integer queryProjectExits(TbProjectTraffic projectTraffic);

    /**
     * 添加
     *
     * @param projectTraffic
     * @return
     */
    int insertProjectTraffic(TbProjectTraffic projectTraffic);

    int updateProjectTraffic(TbProjectTraffic projectTraffic);

    /**
     * 删除项目关联人员
     *
     * @param proId
     * @return
     */
    int deleteProjectPerson(Integer proId);

    /**
     * 添加
     *
     * @param list
     * @return
     */
    int batchTrafficPerson(List<TbTrafficPerson> list);
}