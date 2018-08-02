package com.silita.model;

import com.silita.annotations.FieldChangeRecord;

import java.io.Serializable;

/**
 * @Author:chenzhiqiang
 * @Date:2018/5/14 14:41
 * @Description: 项目基本信息
 */
@FieldChangeRecord(table = "tb_project", id = "pro_id",
        fields = {"pro_name", "pro_org", "pro_where", "pro_address", "invest_amount", "approval_num", "pro_type", "build_type", "acreage", "pro_scope", "pro_level", "pro_use"})
public class Project implements Serializable {
    private static final long serialVersionUID = -1778854576294232827L;
    /**
     * 主键id
     */
    private String pro_id;

    /**
     * 实体MD5
     */
    private String md5;

    /**
     * 网站内部id
     */
    private String xmid;

    /**
     * 项目所属企业名称
     */
    private String com_name;

    /**
     * 项目名称
     */
    private String pro_name;

    /**
     * 省级项目编号
     */
    private String pro_no;

    /***
     * 项目编号
     */
    private String project_no;

    /**
     * 建设单位
     */
    private String pro_org;

    /**
     * 建设单位组织机构代码
     */
    private String pro_org_code;

    /**
     * 所在区划
     */
    private String pro_where;

    /**
     * 项目地点
     */
    private String pro_address;

    /**
     * 总金额
     */
    private String invest_amount;

    /**
     * 立项批准文号
     */
    private String approval_num;

    /**
     * 工程类别
     */
    private String pro_type;

    /**
     * 建设性质
     */
    private String build_type;

    /**
     * 建设面积
     */
    private String acreage;

    /**
     * 工程规模
     */
    private String pro_scope;

    /**
     * 立项级别
     */
    private String pro_level;

    /**
     * 工程用途
     */
    private String pro_use;

    /**
     * url
     */
    private String url;

    public String getXmid() {
        return xmid;
    }

    public void setXmid(String xmid) {
        this.xmid = xmid;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_no() {
        return pro_no;
    }

    public void setPro_no(String pro_no) {
        this.pro_no = pro_no;
    }

    public String getProject_no() {
        return project_no;
    }

    public void setProject_no(String project_no) {
        this.project_no = project_no;
    }

    public String getPro_org() {
        return pro_org;
    }

    public void setPro_org(String pro_org) {
        this.pro_org = pro_org;
    }

    public String getPro_where() {
        return pro_where;
    }

    public void setPro_where(String pro_where) {
        this.pro_where = pro_where;
    }

    public String getPro_address() {
        return pro_address;
    }

    public void setPro_address(String pro_address) {
        this.pro_address = pro_address;
    }

    public String getInvest_amount() {
        return invest_amount;
    }

    public void setInvest_amount(String invest_amount) {
        this.invest_amount = invest_amount;
    }

    public String getApproval_num() {
        return approval_num;
    }

    public void setApproval_num(String approval_num) {
        this.approval_num = approval_num;
    }

    public String getPro_type() {
        return pro_type;
    }

    public void setPro_type(String pro_type) {
        this.pro_type = pro_type;
    }

    public String getBuild_type() {
        return build_type;
    }

    public void setBuild_type(String build_type) {
        this.build_type = build_type;
    }

    public String getAcreage() {
        return acreage;
    }

    public void setAcreage(String acreage) {
        this.acreage = acreage;
    }

    public String getPro_scope() {
        return pro_scope;
    }

    public void setPro_scope(String pro_scope) {
        this.pro_scope = pro_scope;
    }

    public String getPro_level() {
        return pro_level;
    }

    public void setPro_level(String pro_level) {
        this.pro_level = pro_level;
    }

    public String getPro_use() {
        return pro_use;
    }

    public void setPro_use(String pro_use) {
        this.pro_use = pro_use;
    }

    public String getCom_name() {
        return com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
    }

    public String getPro_org_code() {
        return pro_org_code;
    }

    public void setPro_org_code(String pro_org_code) {
        this.pro_org_code = pro_org_code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
