package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Setter
@Getter
public class TbProjectShuili {
    /**
     * 主键id
     */
    private Integer pkid;

    /**
     * 企业id
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
     * 建设单位
     */
    private String proOrg;

    /**
     * 所在地
     */
    private String proWhere;

    /**
     * 工程状态
     */
    private String proStatus;

    /**
     * 合同金额
     */
    private String contractAmount;

    /**
     * 结算金额
     */
    private String clearingAmount;

    /**
     * 开工日期
     */
    private String worked;

    /**
     * 完工日期
     */
    private String finished;

    /**
     * 合同工期
     */
    private String duration;

    /**
     * 关键指标
     */
    private String majorTarget;

    /**
     * 合同主要内容
     */
    private String contractContent;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date updated;

    /**
     * 主要参加人员
     */
    private String persons;

    /**
     * 工程获奖
     */
    private String prizes;
    /**
     * 项目负责人
     */
    private String proLeader;
    /**
     * 技术负责人
     */
    private String techoLeader;

    public TbProjectShuili(Map<String, Object> proMap) {
        this.proName = proMap.get("proName").toString();
        if (null != proMap.get("proType")) {
            this.proType = proMap.get("proType").toString();
        }
        if (null != proMap.get("proOrg")) {
            this.proOrg = proMap.get("proOrg").toString();
        }
        if (null != proMap.get("proWhere")) {
            this.proWhere = proMap.get("proWhere").toString();
        }
        if (null != proMap.get("proStatus")) {
            this.proStatus = proMap.get("proStatus").toString();
        }
        if (null != proMap.get("contractAmount")) {
            this.contractAmount = proMap.get("contractAmount").toString();
        }
        if (null != proMap.get("clearingAmount")) {
            this.clearingAmount = proMap.get("clearingAmount").toString();
        }
        if (null != proMap.get("worked")) {
            this.worked = proMap.get("worked").toString();
        }
        if (null != proMap.get("finished")) {
            this.finished = proMap.get("finished").toString();
        }
        if (null != proMap.get("duration")) {
            this.duration = proMap.get("duration").toString();
        }
        if (null != proMap.get("majorTarget")) {
            this.majorTarget = proMap.get("majorTarget").toString();
        }
        if (null != proMap.get("contractContent")) {
            this.contractContent = proMap.get("contractContent").toString();
        }
        if (null != proMap.get("proManager")) {
            this.proLeader = proMap.get("proManager").toString();
        }
        if (null != proMap.get("techManager")) {
            this.techoLeader = proMap.get("techManager").toString();
        }
    }
}