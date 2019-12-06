package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SkyProjZbChongq {
    /**
     * 主键id
     */
    private String pkid;

    /**
     * 项目名称
     */
    private String projName;

    /**
     * 招标编码
     */
    private String zhaobiaoCode;

    /**
     * 招标人
     */
    private String zhaobiaoPerson;

    /**
     * 企业id
     */
    private String comId;

    /**
     * 中标企业名称
     */
    private String comName;

    /**
     * 项目经理
     */
    private String projManager;

    /**
     * 中标价(万元)
     */
    private String amount;

    /**
     * 中标工期(天)
     */
    private String days;

    /**
     * 层数
     */
    private String floorNum;

    /**
     * 开标日期
     */
    private String pubDate;

    /**
     * 工程规模
     */
    private String scope;

    /**
     * 修改时间
     */
    private Date updated;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 中标范围和内容
     */
    private String content;

    /**
     * 榨取url
     */
    private String url;
}