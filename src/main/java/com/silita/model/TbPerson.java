package com.silita.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by zhushuai on 2019/11/11.
 */
@Setter
@Getter
public class TbPerson {

    private String pkid;
    private String name;
    private String sex;
    private String idCard;
    private String category;
    private String certNo;
    private String major;
    private String certDate;
    private String comName;
    private String comId;
    private String validDate;
    private Integer type;
    private String perId;
    private String url;
    private String tabCode;

    public String getUrl() {
        return "https://glxy.mot.gov.cn/person/base.do?id=" + perId + "&type=" + type + "&companyid=" + comId;
    }

}
