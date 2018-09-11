package com.silita.spider.common.model;

import com.silita.annotations.FieldChangeRecord;

import java.io.Serializable;

/**
 * @Author:chenzhiqiang
 * @Date:2018/5/14 19:39
 * @Description: 招投标
 */
@FieldChangeRecord(table = "tb_project_zhaotoubiao", id = "pkid",
        fields = {"zhaobiao_type", "zhaobiao_way", "zhongbiao_date", "build_scale", "area", "zhongbiao_amount", "zhaobiao_agency_company", "zhongbiao_company", "record_date"})
public class ZhaoTouBiao implements Serializable {
    private static final long serialVersionUID = -3512445499815864995L;
    /**
     * 主键id
     */
    private String pkid;

    /**
     * 实体MD5
     */
    private String md5;

    /**
     * 项目id
     */
    private String pro_id;

    /**
     * 中标通知书编号
     */
    private String zhongbiao_code;

    /**
     * 省级中标通知书编号
     */
    private String prov_zhongbiao_code;

    /**
     * 招标类型
     */
    private String zhaobiao_type;

    /**
     * 招标方式
     */
    private String zhaobiao_way;

    /**
     * 中标日期
     */
    private String zhongbiao_date;

    /**
     * 建设规模
     */
    private String build_scale;

    /**
     * 面积
     */
    private String area;

    /**
     * 中标金额
     */
    private String zhongbiao_amount;

    /**
     * 招标代理单位名称
     */
    private String zhaobiao_agency_company;

    /**
     * 招标代理单位组织机构代码
     */
    private String zhaobiao_company_code;

    /**
     * 中标单位名称
     */
    private String zhongbiao_company;

    /**
     * 中标单位组织机构代码
     */
    private String zhongbiao_company_code;

    /**
     * 记录登记时间
     */
    private String record_date;

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }

    public String getZhongbiao_code() {
        return zhongbiao_code;
    }

    public void setZhongbiao_code(String zhongbiao_code) {
        this.zhongbiao_code = zhongbiao_code;
    }

    public String getProv_zhongbiao_code() {
        return prov_zhongbiao_code;
    }

    public void setProv_zhongbiao_code(String prov_zhongbiao_code) {
        this.prov_zhongbiao_code = prov_zhongbiao_code;
    }

    public String getZhaobiao_type() {
        return zhaobiao_type;
    }

    public void setZhaobiao_type(String zhaobiao_type) {
        this.zhaobiao_type = zhaobiao_type;
    }

    public String getZhaobiao_way() {
        return zhaobiao_way;
    }

    public void setZhaobiao_way(String zhaobiao_way) {
        this.zhaobiao_way = zhaobiao_way;
    }

    public String getZhongbiao_date() {
        return zhongbiao_date;
    }

    public void setZhongbiao_date(String zhongbiao_date) {
        this.zhongbiao_date = zhongbiao_date;
    }

    public String getZhongbiao_amount() {
        return zhongbiao_amount;
    }

    public void setZhongbiao_amount(String zhongbiao_amount) {
        this.zhongbiao_amount = zhongbiao_amount;
    }

    public String getZhaobiao_agency_company() {
        return zhaobiao_agency_company;
    }

    public void setZhaobiao_agency_company(String zhaobiao_agency_company) {
        this.zhaobiao_agency_company = zhaobiao_agency_company;
    }

    public String getZhaobiao_company_code() {
        return zhaobiao_company_code;
    }

    public void setZhaobiao_company_code(String zhaobiao_company_code) {
        this.zhaobiao_company_code = zhaobiao_company_code;
    }

    public String getZhongbiao_company() {
        return zhongbiao_company;
    }

    public void setZhongbiao_company(String zhongbiao_company) {
        this.zhongbiao_company = zhongbiao_company;
    }

    public String getZhongbiao_company_code() {
        return zhongbiao_company_code;
    }

    public void setZhongbiao_company_code(String zhongbiao_company_code) {
        this.zhongbiao_company_code = zhongbiao_company_code;
    }

    public String getRecord_date() {
        return record_date;
    }

    public void setRecord_date(String record_date) {
        this.record_date = record_date;
    }

    public String getBuild_scale() {
        return build_scale;
    }

    public void setBuild_scale(String build_scale) {
        this.build_scale = build_scale;
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
}
