package com.silita.model;

import java.io.Serializable;

/**
 * @Author:chenzhiqiang
 * @Date:2018/5/14 9:59
 * @Description: 人员变更
 */
public class PersonChange implements Serializable {
    private static final long serialVersionUID = 4879853596810173370L;
    /**
     * 主键id
     */
    private int pkid;

    /**
     * 公司名称
     */
    private String com_name;

    /**
     * 注册专业
     */
    private String major;

    /**
     * 变更日期
     */
    private String change_date;

    /**
     * 备注
     */
    private String remark;

    /**
     * 人员唯一标志
     */
    private String flag;

    public int getPkid() {
        return pkid;
    }

    public void setPkid(int pkid) {
        this.pkid = pkid;
    }

    public String getCom_name() {
        return com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getChange_date() {
        return change_date;
    }

    public void setChange_date(String change_date) {
        this.change_date = change_date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "PersonChange{" +
                "pkid=" + pkid +
                ", com_name='" + com_name + '\'' +
                ", major='" + major + '\'' +
                ", change_date='" + change_date + '\'' +
                ", remark='" + remark + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
