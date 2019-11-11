package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TbCompany {
    /**
     * 企业ID
     */
    private String comId;

    /**
     * 企业名称
     */
    private String comName;

    /**
     * 企业名称首字母
     */
    private String comNamePy;

    /**
     * 组织机构代码
     */
    private String orgCode;

    /**
     * 工商营业执照
     */
    private String businessNum;

    /**
     * 注册地址
     */
    private String regisAddress;

    /**
     * 企业营业地址
     */
    private String comAddress;

    /**
     * 法人
     */
    private String legalPerson;

    /**
     * 经济类型
     */
    private String economicType;

    /**
     * 注册资本
     */
    private String regisCapital;

    /**
     * 技术负责人
     */
    private String skillLeader;

    /**
     * 渠道来源(四库一，公路，水利)
     */
    private String channel;

    /**
     * 资质
     */
    private String range;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;

    /**
     * md5
     */
    private String md5;

    /**
     * 统一信用代码
     */
    private String creditCode;
}