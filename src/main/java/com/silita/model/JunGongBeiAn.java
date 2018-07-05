package com.silita.model;

import java.io.Serializable;

/**
 * @Author:chenzhiqiang
 * @Date:2018/5/16 16:28
 * @Description: 竣工验收备案
 */
public class JunGongBeiAn implements Serializable {
    private static final long serialVersionUID = 2684221160416165135L;
    /**
     * 主键id
     */
    private int pkid;

    /**
     * 项目id
     */
    private int pro_id;

    /**
     * 竣工备案编号
     */
    private String code;

    /**
     * 省级竣工备案编号
     */
    private String prov_code;

    /**
     * 实际造价
     */
    private String cost;

    /**
     * 实际面积
     */
    private String area;

    /**
     * 实际建设规模
     */
    private String build_scale;

    /**
     * 结构体系
     */
    private String struct;

    /**
     * 实际开工日期
     */
    private String build_start;

    /**
     * 实际竣工验收日期
     */
    private String build_end;

    /**
     * 记录登记时间
     */
    private String record_date;

    /**
     * 备注
     */
    private String remark;

    public int getPkid() {
        return pkid;
    }

    public void setPkid(int pkid) {
        this.pkid = pkid;
    }

    public int getPro_id() {
        return pro_id;
    }

    public void setPro_id(int pro_id) {
        this.pro_id = pro_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProv_code() {
        return prov_code;
    }

    public void setProv_code(String prov_code) {
        this.prov_code = prov_code;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBuild_scale() {
        return build_scale;
    }

    public void setBuild_scale(String build_scale) {
        this.build_scale = build_scale;
    }

    public String getStruct() {
        return struct;
    }

    public void setStruct(String struct) {
        this.struct = struct;
    }

    public String getBuild_start() {
        return build_start;
    }

    public void setBuild_start(String build_start) {
        this.build_start = build_start;
    }

    public String getBuild_end() {
        return build_end;
    }

    public void setBuild_end(String build_end) {
        this.build_end = build_end;
    }

    public String getRecord_date() {
        return record_date;
    }

    public void setRecord_date(String record_date) {
        this.record_date = record_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "JunGongBeiAn{" +
                "pkid=" + pkid +
                ", pro_id=" + pro_id +
                ", code='" + code + '\'' +
                ", prov_code='" + prov_code + '\'' +
                ", cost='" + cost + '\'' +
                ", area='" + area + '\'' +
                ", build_scale='" + build_scale + '\'' +
                ", struct='" + struct + '\'' +
                ", build_start='" + build_start + '\'' +
                ", build_end='" + build_end + '\'' +
                ", record_date='" + record_date + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
