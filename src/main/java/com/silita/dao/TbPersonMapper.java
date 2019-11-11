package com.silita.dao;


import com.silita.model.TbPerson;

public interface TbPersonMapper {

    /**
     * 查询是否存在该人员证书
     *
     * @param person
     * @return
     */
    String queryPersonCertExist(TbPerson person);

    /**
     * 修改更新时间
     *
     * @return
     */
    int updateDate(TbPerson person);

    /**
     * 添加人员证书
     *
     * @param person
     * @return
     */
    int insertPerson(TbPerson person);
}