package com.silita.service;

import com.google.common.collect.Maps;
import com.silita.common.RegionCommon;
import com.silita.dao.AliasMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 资质等级别名公共方法类
 * Created by zhushuai on 2019/11/28.
 */
@Service
public class AliasService {

    @Autowired
    private AliasMapper aliasMapper;

    /**
     * 添加资质等级别名
     *
     * @param alias 资质或等级别名
     * @param type  别名类型(1:资质，2:等级)
     */
    public void saveAlias(String alias, int type) {
        String count = aliasMapper.queryAnalysisAliasExist(alias);
        if (StringUtils.isNotEmpty(count)) {
            return;
        }
        Map<String, Object> aliasMap = Maps.newHashMapWithExpectedSize(2);
        {
            {
                aliasMap.put("aliasName", alias);
                aliasMap.put("type", type);
            }
        }
        aliasMapper.insertAnalysisAlias(aliasMap);
    }

    /**
     * 保存资质备案
     *
     * @param qual     资质名称
     * @param province 备案省份
     */
    public void saveQualRegion(String qual, String province) {
        Integer index = RegionCommon.regionBeiAnIndex.get(province);
        if (null == index) {
            return;
        }
        Map<String, Object> qualMap = aliasMapper.queryQualificationRegionExist(qual);
        if (MapUtils.isEmpty(qualMap)) {
            StringBuffer inRegion = new StringBuffer("0000000000000000000000000000000");
            inRegion.setCharAt(index, '1');
            qualMap = Maps.newHashMap();
            {
                {
                    qualMap.put("qualName", qual);
                    qualMap.put("inRegion", inRegion.toString());
                }
            }
            aliasMapper.insertqueryQualificationRegion(qualMap);
        } else {
            StringBuffer inRegion = new StringBuffer(qualMap.get("in_region").toString());
            inRegion.setCharAt(index, '1');
            qualMap.put("inRegion", inRegion.toString());
            aliasMapper.updatequeryQualificationRegion(qualMap);
        }
    }
}
