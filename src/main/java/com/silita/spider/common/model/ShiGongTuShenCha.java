package com.silita.spider.common.model;

import com.silita.annotations.FieldChangeRecord;

import java.io.Serializable;

/**
 * @Author:chenzhiqiang
 * @Date:2018/5/15 11:39
 * @Description: 施工图审查
 */
@FieldChangeRecord(table = "tb_project_design", id = "pkid",
        fields = {"explore_org", "design_org", "check_org", "check_finish_date"})
public class ShiGongTuShenCha implements Serializable {
    private static final long serialVersionUID = -2230809082870825201L;
    /**
     * 主键id
     */
    private String pkid;

    /**
     * 实体MD5
     */
    private String md5;

    /**
     * 网站内部id
     */
    private String sgtxh;

    /**
     * 项目id
     */
    private String pro_id;

    /**
     * 项目名称
     */
    private String pro_name;

    /**
     * 项目类型
     */
    private String pro_type;

    /**
     * 项目规模
     */
    private String pro_scope;

    /**
     * 勘察单位
     */
    private String explore_org;

    /**
     * 设计单位
     */
    private String design_org;

    /**
     * 施工图审查单位
     */
    private String check_org;

    /**
     * 施工图审查机构代码
     */
    private String check_org_code;

    /**
     * 省级施工图审查合格书编号
     */
    private String check_no;

    /**
     * 施工图审查合格书编号
     */
    private String check_number;

    /**
     * 施工图审查完成日期
     */
    private String check_finish_date;

    public String getSgtxh() {
        return sgtxh;
    }

    public void setSgtxh(String sgtxh) {
        this.sgtxh = sgtxh;
    }

    public String getCheck_org() {
        return check_org;
    }

    public void setCheck_org(String check_org) {
        this.check_org = check_org;
    }

    public String getCheck_no() {
        return check_no;
    }

    public void setCheck_no(String check_no) {
        this.check_no = check_no;
    }

    public String getCheck_number() {
        return check_number;
    }

    public void setCheck_number(String check_number) {
        this.check_number = check_number;
    }

    public String getCheck_finish_date() {
        return check_finish_date;
    }

    public void setCheck_finish_date(String check_finish_date) {
        this.check_finish_date = check_finish_date;
    }

    public String getPro_name() {
        return pro_name;
    }

    public void setPro_name(String pro_name) {
        this.pro_name = pro_name;
    }

    public String getPro_type() {
        return pro_type;
    }

    public void setPro_type(String pro_type) {
        this.pro_type = pro_type;
    }

    public String getPro_scope() {
        return pro_scope;
    }

    public void setPro_scope(String pro_scope) {
        this.pro_scope = pro_scope;
    }

    public String getExplore_org() {
        return explore_org;
    }

    public void setExplore_org(String explore_org) {
        this.explore_org = explore_org;
    }

    public String getDesign_org() {
        return design_org;
    }

    public void setDesign_org(String design_org) {
        this.design_org = design_org;
    }

    public String getCheck_org_code() {
        return check_org_code;
    }

    public void setCheck_org_code(String check_org_code) {
        this.check_org_code = check_org_code;
    }

    public String getPkid() {
        return pkid;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }
}
