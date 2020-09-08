package com.silita.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.MapUtils;
import org.jsoup.Jsoup;

import java.util.Date;

@Setter
@Getter
public class TbProjectTraffic {
    /**
     * 主键id
     */
    private Integer pkid;

    /**
     * 施工单位
     */
    private String comName;

    /**
     * 项目名称
     */
    private String proName;

    /**
     * 项目类别
     */
    private String proType;

    /**
     * 标段名
     */
    private String section;

    /**
     * 项目地区
     */
    private String proWhere;

    /**
     * 信息来源
     */
    private String source;

    /**
     * 建设状态
     */
    private String build;

    /**
     * 技术等级
     */
    private String technicalGrade;

    /**
     * 合同价格
     */
    private String contractAmount;

    /**
     * 结算价格
     */
    private String clearingAmount;

    /**
     * 开工日期
     */
    private String begined;

    /**
     * 交工日期
     */
    private String ended;

    /**
     * 竣工时间
     */
    private String completed;

    /**
     * 合同段开始桩号
     */
    private String stakeBegin;

    /**
     * 合同段结束桩号
     */
    private String stakeEnd;

    /**
     * 质量评定情况
     */
    private String assess;

    /**
     * 公路里程
     */
    private Double acreage;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date updated;

    /**
     * 主要工程量
     */
    private String remark;

    public TbProjectTraffic(JSONObject jsonObject) {
        this.comName = MapUtils.getString(jsonObject, "corpName");
        this.proName = Jsoup.parse(MapUtils.getString(jsonObject, "projectName")).text();
        this.section = MapUtils.getString(jsonObject, "segmentName");
        this.proWhere = MapUtils.getString(jsonObject, "province");
        this.source = MapUtils.getString(jsonObject, "sourceInfo");
        this.build = MapUtils.getString(jsonObject, "projectStatus");
        this.technicalGrade = MapUtils.getString(jsonObject, "technologyGrade");
        this.contractAmount = MapUtils.getString(jsonObject, "contractPrice");
        this.clearingAmount = MapUtils.getString(jsonObject, "settlementPrice");
        this.begined = MapUtils.getString(jsonObject, "beginDate");
        this.ended = MapUtils.getString(jsonObject, "handDate");
        this.completed = MapUtils.getString(jsonObject, "endDate");
        this.stakeBegin = MapUtils.getString(jsonObject, "stakeStart");
        this.stakeEnd = MapUtils.getString(jsonObject, "stakeEnd");
        this.assess = MapUtils.getString(jsonObject, "quality");
        this.remark = MapUtils.getString(jsonObject, "remark");
        this.proType = MapUtils.getString(jsonObject, "projectType");
    }
}