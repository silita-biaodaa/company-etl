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

    /**
     * 根据等级别名查询等级code
     * @param grade
     * @return
     */
    String queryGradeCode(String grade);

    /**
     * 根据等级别名查询等级code和标准名
     * @param alias
     * @return
     */
    Map<String,Object> queryGradeByAlias(String alias);
}