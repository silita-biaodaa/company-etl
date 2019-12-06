package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SkyProjectCompany {
    /**
     * 主键id
     */
    private Integer pkid;

    /**
     * 项目id
     */
    private String proId;

    /**
     * 企业id
     */
    private String comId;

    /**
     * 企业名称
     */
    private String comName;

    /**
     * 角色(build:施工,explore:勘察,design:设计,supervision:监理,zhongbiao:中标)
     */
    private String role;

    /**
     * 表名后缀
     */
    private String tab;

    /**
     * 修改时间
     */
    private Date updated;

    /**
     * 创建时间
     */
    private Date created;
}