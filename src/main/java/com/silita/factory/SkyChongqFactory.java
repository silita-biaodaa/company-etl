package com.silita.factory;

import com.alibaba.fastjson.JSONObject;
import com.silita.service.SkyChongqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhushuai on 2019/11/11.
 */
@Component
public class SkyChongqFactory {

    @Autowired
    SkyChongqService skyChongqService;

    public void process(JSONObject object) {
        skyChongqService.analysisCompanyInfo(object);
    }
}
