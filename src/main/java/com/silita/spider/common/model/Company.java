package com.silita.spider.common.model;

import com.silita.annotations.FieldChangeRecord;

import java.io.Serializable;

/**
 * @Author:chenzhiqiang
 * @Date:2018/5/8 11:53
 * @Description: 企业基本信息
 */
@FieldChangeRecord(table = "tb_company", id = "com_id",
        fields = {"regis_address", "com_address", "legal_person", "economic_type", "regis_capital"})
public class Company implements Serializable {
    private static final long serialVersionUID = 2680866379272673386L;

    /**
     * 实体MD5
     */
    private String md5;

    /**
     * 企业id
     */
    private String com_id;
    /**
     * 企业名称
     */
    private String com_name;

    /**
     * 企业名称首字母
     */
    private String com_name_py;

    /**
     * 企业类型
     */
    private String type;

    /**
     * 统一社会信用代码
     */
    private String credit_code;

    /**
     * 组织机构代码
     */
    private String org_code;
    /**
     * 工商营业执照
     */
    private String business_num;
    /**
     * 注册地址
     */
    private String regis_address;
    /**
     * 企业营业地址
     */
    private String com_address;
    /**
     * 法人
     */
    private String legal_person;
    /**
     * 经济类型
     */
    private String economic_type;
    /**
     * 注册资本
     */
    private String regis_capital;

    /**
     * url
     */
    private String url;

    private String range;

    public String getCom_id() {
        return com_id;
    }

    public void setCom_id(String com_id) {
        this.com_id = com_id;
    }

    public String getCom_name() {
        return com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getBusiness_num() {
        return business_num;
    }

    public void setBusiness_num(String business_num) {
        this.business_num = business_num;
    }

    public String getRegis_address() {
        return regis_address;
    }

    public void setRegis_address(String regis_address) {
        this.regis_address = regis_address;
    }

    public String getCom_address() {
        return com_address;
    }

    public void setCom_address(String com_address) {
        this.com_address = com_address;
    }

    public String getLegal_person() {
        return legal_person;
    }

    public void setLegal_person(String legal_person) {
        this.legal_person = legal_person;
    }

    public String getEconomic_type() {
        return economic_type;
    }

    public void setEconomic_type(String economic_type) {
        this.economic_type = economic_type;
    }

    public String getRegis_capital() {
        return regis_capital;
    }

    public void setRegis_capital(String regis_capital) {
        this.regis_capital = regis_capital;
    }

    public String getCredit_code() {
        return credit_code;
    }

    public void setCredit_code(String credit_code) {
        this.credit_code = credit_code;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getCom_name_py() {
        return com_name_py;
    }

    public void setCom_name_py(String com_name_py) {
        this.com_name_py = com_name_py;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
