package com.silita.model;

import java.io.Serializable;

/**
 * @Author:chenzhiqiang
 * @Date:2018/5/15 14:08
 * @Description: 项目与公司在项目中承担的角色的关系
 */
public class ProjectCompany implements Serializable {
    private static final long serialVersionUID = 7872775545361658507L;
    /**
     * 主键id
     */
    private int pkid;

    /**
     * 企业内部id
     */
    private String innerId;

    /**
     * 项目id
     */
    private int pro_id;

    /**
     * 子项目id
     */
    private int pid;

    /**
     * 涉及角色
     */
    private String role;

    /**
     * 企业名称
     */
    private String com_name;

    /**
     * 组织机构代码
     */
    private String org_code;

    /**
     * 所在省份
     */
    private String province;

    /**
     * 所关联的表名后缀
     */
    private String type;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCom_name() {
        return com_name;
    }

    public void setCom_name(String com_name) {
        this.com_name = com_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getInnerId() {
        return innerId;
    }

    public void setInnerId(String innerId) {
        this.innerId = innerId;
    }

    public String getOrg_code() {
        return org_code;
    }

    public void setOrg_code(String org_code) {
        this.org_code = org_code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
