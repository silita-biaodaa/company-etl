package com.silita.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class SkyProjGcChongq {
    /**
     * 主键id
     */
    private Integer pkid;

    /**
     * 项目名称
     */
    private String projName;

    /**
     * 项目结构类型
     */
    private String projType;

    /**
     * 建设地点
     */
    private String projAddress;

    /**
     * 建设单位
     */
    private String projCom;

    /**
     * 企业id
     */
    private String comId;

    /**
     * 企业名称
     */
    private String comName;

    /**
     * 企业在项目中的角色(build:施工,explore:勘察,design:设计,supervision:监理)
     */
    private String role;

    /**
     * 项目经理
     */
    private String projManager;

    /**
     * 总监理工程师
     */
    private String supervisionPerson;

    /**
     * 勘察单位项目负责人
     */
    private String explorePerson;

    /**
     * 设计单位项目负责人
     */
    private String designPerson;

    /**
     * 承包规模
     */
    private String contractScope;

    /**
     * 建设规模
     */
    private String buildScope;

    /**
     * 合同价格(万元)
     */
    private String amount;

    /**
     * 面积
     */
    private String area;

    /**
     * 合同工期
     */
    private String days;

    /**
     * 竣工时间
     */
    private String end;

    /**
     * 开工时间
     */
    private String start;

    /**
     * 发证日期
     */
    private String issueDate;

    /**
     * 发证机关
     */
    private String issueOrg;

    /**
     * 使用年限
     */
    private String durableYears;

    /**
     * 业绩类型,（complete：竣工，build：施工）
     */
    private String type;

    /**
     * 修改时间
     */
    private Date updated;

    /**
     * 创建时间
     */
    private Date created;
}