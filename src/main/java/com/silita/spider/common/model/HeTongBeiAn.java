package com.silita.spider.common.model;

import com.silita.annotations.FieldChangeRecord;

import java.io.Serializable;

/**
 * @Author:chenzhiqiang
 * @Date:2018/5/16 10:14
 * @Description: 合同备案
 */
@FieldChangeRecord(table = "tb_project_contract", id = "pkid",
        fields = {"category", "type", "amount", "build_scale", "sign_date", "record_date"})
public class HeTongBeiAn implements Serializable {
    private static final long serialVersionUID = 3115265977969611492L;
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
     * 合同备案编号
     */
    private String record_code;

    /**
     * 省级合同备案编号
     */
    private String prov_record_code;

    /**
     * 合同编号
     */
    private String code;

    /**
     * 合同分类
     */
    private String category;

    /**
     * 合同类别
     */
    private String type;

    /**
     * 合同金额
     */
    private String amount;

    /**
     * 建设规模
     */
    private String build_scale;

    /**
     * 合同签订日期
     */
    private String sign_date;

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

    public String getRecord_code() {
        return record_code;
    }

    public void setRecord_code(String record_code) {
        this.record_code = record_code;
    }

    public String getProv_record_code() {
        return prov_record_code;
    }

    public void setProv_record_code(String prov_record_code) {
        this.prov_record_code = prov_record_code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBuild_scale() {
        return build_scale;
    }

    public void setBuild_scale(String build_scale) {
        this.build_scale = build_scale;
    }

    public String getSign_date() {
        return sign_date;
    }

    public void setSign_date(String sign_date) {
        this.sign_date = sign_date;
    }

    public String getRecord_date() {
        return record_date;
    }

    public void setRecord_date(String record_date) {
        this.record_date = record_date;
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
