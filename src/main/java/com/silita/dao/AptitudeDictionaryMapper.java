package com.silita.dao;


import java.util.Map;

public interface AptitudeDictionaryMapper {
    /**
     *
     * @param majorUuid
     * @return
     */
    String getMajorNameBymajorUuid(String majorUuid);

    /**
     * 根据别名查询code
     * @param name
     * @return
     */
    String queryCodeByAlias(String name);

    /**
     * 查询资质等级主键id
     * @param param
     * @return
     */
    String queryPkidByParam(Map<String,Object> param);

    /**
     * 根据code查询名称
     * @param qualCode
     * @return
     */
    String queryQualNameByCode(String qualCode);

    String getQulId(String name);
}