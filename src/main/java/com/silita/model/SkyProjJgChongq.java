package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SkyProjJgChongq {
    /**
     * 主键id
     */
    private String pkid;

    /**
     * 项目名称
     */
    private String projName;

    /**
     * 建设单位
     */
    private String proOrg;

    /**
     * 工程地址
     */
    private String projAddress;

    /**
     * 登记号
     */
    private String sectionCode;

    /**
     * 主体结构类型
     */
    private String projType;

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
     * 面积
     */
    private String area;

    /**
     * 价格(万元)
     */
    private String amount;

    /**
     * 使用年限
     */
    private String days;

    /**
     * 竣工时间
     */
    private String ended;

    /**
     * 备案日期
     */
    private String issued;

    /**
     * 本次备案工程
     */
    private String section;

    /**
     * 修改时间
     */
    private Date updated;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 爬取链接
     */
    private String url;

}