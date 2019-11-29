package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class TbCompanyPunish {
    /**
     * 主键id
     */
    private Integer pkid;

    /**
     * 行政处罚决定书文号
     */
    private String punishCode;

    /**
     * 处罚类别
     */
    private String punishType;

    /**
     * 处罚名称
     */
    private String punishName;

    /**
     * 处罚事由
     */
    private String punishContent;

    /**
     * 处罚依据
     */
    private String punishWith;

    /**
     * 处罚结果
     */
    private String punishResult;

    /**
     * 处罚决定日期
     */
    private String punishDate;

    /**
     * 处罚机关
     */
    private String punishOrg;

    /**
     * 企业id
     */
    private String comId;

    /**
     * 抓取url
     */
    private String url;

    /**
     * 创建时间
     */
    private Date created;
}