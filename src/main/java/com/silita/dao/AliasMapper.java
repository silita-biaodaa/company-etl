package com.silita.dao;


import java.util.Map;

public interface AliasMapper {

    /**
     * 查询资质或等级别名是否存在
     * @param alias
     * @return
     */
    String queryAnalysisAliasExist(String alias);

    /**
     * 添加等级资质别名
     * @param param
     * @return
     */
    int insertAnalysisAlias(Map<String,Object> param);

    /**
     * 查询资质备案
     * @param qual
     * @return
     */
    Map<String,Object> queryQualificationRegionExist(String qual);

    /**
     * 添加资质备案
     * @param param
     * @return
     */
    int insertqueryQualificationRegion(Map<String,Object> param);

    /**
     * 修改资质备案
     * @param param
     * @return
     */
    int updatequeryQualificationRegion(Map<String,Object> param);
}
