package com.silita.dao;


import com.silita.model.AllZh;

public interface AllZhMapper {
    /**
     * 根据资质名称，获取资质信息
     * @param name
     * @return
     */
    AllZh getAllZhByName(String name);
}