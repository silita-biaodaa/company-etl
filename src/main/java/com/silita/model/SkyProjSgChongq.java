package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SkyProjSgChongq {
    /**
     * 主键id
     */
    private String pkid;

    /**
     * 项目名称
     */
    private String projName;

    /**
     * 项目状态
     */
    private String projState;

    /**
     * 建设地点
     */
    private String projAddress;

    /**
     * 建设单位
     */
    private String proOrg;

    /**
     * 施工企业
     */
    private String bOrg;

    /**
     * 勘查单位
     */
    private String exploreOrg;

    /**
     * 设计单位
     */
    private String designOrg;

    /**
     * 监理单位
     */
    private String superOrg;

    /**
     * 承包范围
     */
    private String scope;

    /**
     * 面积
     */
    private String area;

    /**
     * 项目经理
     */
    private String projManager;

    /**
     * 勘察单位项目负责人
     */
    private String exploreManager;

    /**
     * 总监理工程师
     */
    private String superManager;

    /**
     * 设计单位项目负责人
     */
    private String designManager;

    /**
     * 合同价格(万元)
     */
    private String amount;

    /**
     * 合同工期
     */
    private String days;

    /**
     * 开工日期
     */
    private String begined;

    /**
     * 竣工时间
     */
    private String ended;

    /**
     * 发证日期
     */
    private String issued;

    /**
     * 发证机关
     */
    private String issueOrg;

    /**
     * 修改时间
     */
    private Date updated;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 建设规模
     */
    private String buildScale;

    /**
     * 爬取链接
     */
    private String url;
}