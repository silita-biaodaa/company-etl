package com.silita.model;

import java.io.Serializable;

/**
 * @Author:chenzhiqiang
 * @Date:2018/5/16 8:54
 * @Description: 施工许可
 */
public class ShiGongXuKe implements Serializable {
    private static final long serialVersionUID = -1344842768765930116L;
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
    private String bdxh;

    /**
     * 项目id
     */
    private String pro_id;

    /**
     * 项目名称
     */
    private String pro_name;

    /**
     * 项目类别/分类
     */
    private String pro_type;

    /**
     * 施工单位/建设单位
     */
    private String b_org;

    /**
     * 省级施工许可证号
     */
    private String b_licence;

    /**
     * 施工许可证号
     */
    private String build_licence;

    /**
     * 施工图审查合格书编号
     */
    private String review_number;

    /**
     * 合同金额
     */
    private String amount;

    /**
     * 面积
     */
    private String area;

    /**
     * 记录登记时间
     */
    private String record_date;

    public String getBdxh() {
        return bdxh;
    }

    public void setBdxh(String bdxh) {
        this.bdxh = bdxh;
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

    public String getB_licence() {
        return b_licence;
    }

    public void setB_licence(String b_licence) {
        this.b_licence = b_licence;
    }

    public String getBuild_licence() {
        return build_licence;
    }

    public void setBuild_licence(String build_licence) {
        this.build_licence = build_licence;
    }

    public String getReview_number() {
        return review_number;
    }

    public void setReview_number(String review_number) {
        this.review_number = review_number;
    }

    public String getRecord_date() {
        return record_date;
    }

    public void setRecord_date(String record_date) {
        this.record_date = record_date;
    }

    public String getB_org() {
        return b_org;
    }

    public void setB_org(String b_org) {
        this.b_org = b_org;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
