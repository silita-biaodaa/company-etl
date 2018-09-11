package com.silita.spider.common.model;

/**
 * @Author:chenzhiqiang
 * @Date:2018/7/27 10:42
 * @Description:字段变更记录
 */
public class FieldChange {
    /**
     * id
     */
    private int id;

    /**
     * 表名
     */
    private String tb_name;
    /**
     * 表中id
     */
    private String tb_id;
    /**
     * 列名
     */
    private String column_name;
    /**
     * 旧值
     */
    private String old_value;
    /**
     * 新值
     */
    private String new_value;

    public String getTb_name() {
        return tb_name;
    }

    public void setTb_name(String tb_name) {
        this.tb_name = tb_name;
    }

    public String getTb_id() {
        return tb_id;
    }

    public void setTb_id(String tb_id) {
        this.tb_id = tb_id;
    }

    public String getColumn_name() {
        return column_name;
    }

    public void setColumn_name(String column_name) {
        this.column_name = column_name;
    }

    public String getOld_value() {
        return old_value;
    }

    public void setOld_value(String old_value) {
        this.old_value = old_value;
    }

    public String getNew_value() {
        return new_value;
    }

    public void setNew_value(String new_value) {
        this.new_value = new_value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "字段变更记录{" +
                "tb_name='" + tb_name + '\'' +
                ", tb_id='" + tb_id + '\'' +
                ", column_name='" + column_name + '\'' +
                ", old_value='" + old_value + '\'' +
                ", new_value='" + new_value + '\'' +
                '}';
    }
}
