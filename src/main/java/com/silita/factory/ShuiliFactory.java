package com.silita.factory;

import com.alibaba.fastjson.JSONObject;
import com.silita.service.CompanyShuiliService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhushuai on 2019/11/11.
 */
@Component
public class ShuiliFactory {

    @Autowired
    CompanyShuiliService shuiliService;

    public void process(JSONObject object) {
        shuiliService.analysisCompanyInfo(object);
    }
}
