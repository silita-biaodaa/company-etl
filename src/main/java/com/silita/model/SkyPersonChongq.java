package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SkyPersonChongq {
    /**
     * 主键id
     */
    private Integer pkid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String sex;

    /**
     * 注册类别
     */
    private String category;

    /**
     * 专业
     */
    private String major;

    /**
     * 注册号
     */
    private String certNo;

    /**
     * 注册日期
     */
    private String certDate;

    /**
     * 有效期
     */
    private String validDate;

    /**
     * 企业id
     */
    private String comId;

    /**
     * 是否有效
     */
    private Integer isValid;

    /**
     * 是否在全国注册(0:否，1:是)
     */
    private Integer isAll;

    /**
     * 修改详情
     */
    private Date updated;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 抓取url
     */
    private String url;

    private String tabCode;
}