package com.silita.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.MapUtils;

import java.util.Date;
import java.util.Map;

@Setter
@Getter
public class TbTrafficPerson {
    /**
     * 主键id
     */
    private Integer pkid;

    /**
     * 项目id
     */
    private Integer proId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 岗位
     */
    private String post;

    /**
     * 任职开始日期
     */
    private String begined;

    /**
     * 任职结束日期
     */
    private String ended;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date updated;

    public TbTrafficPerson(Integer proId, Map map){
        this.proId = proId;
        this.name = MapUtils.getString(map,"personName");
        this.post = MapUtils.getString(map,"achievementPosition");
        this.begined = MapUtils.getString(map,"achievementStartDate");
        this.ended = MapUtils.getString(map,"achievementEndDate");
    }

}