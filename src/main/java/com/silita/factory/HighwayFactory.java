package com.silita.factory;

import com.alibaba.fastjson.JSONObject;
import com.silita.service.CompanyHighwayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhushuai on 2019/11/11.
 */
@Component
public class HighwayFactory {

    @Autowired
    CompanyHighwayService companyHighwayService;

    public void process(JSONObject object) {
        String topicType = object.getString("topicType");
        switch (topicType) {
            case "company_info":
                companyHighwayService.analysisCompanyInfo(object);
                return;
            case "person_cert":
                companyHighwayService.analysisCompanyPersonCert(object);
                return;
            case "project_person":
                companyHighwayService.analysisCompanyProject(object);
                return;
            case "company_quals":
                companyHighwayService.analysisCompanyQuals(object);
                return;
        }
    }
}
