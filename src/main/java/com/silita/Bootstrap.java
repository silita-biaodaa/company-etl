package com.silita;

import com.silita.service.DeleteCostCompanyService;
import com.silita.service.IAptitudeCleanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements ApplicationListener<ApplicationEvent> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAptitudeCleanService aptitudeCleanService;

    @Autowired
    private DeleteCostCompanyService deleteCostCompanyService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            //清洗资质
            /*aptitudeCleanService.splitAllCompanyAptitude();
            aptitudeCleanService.updateAllCompanyAptitude();*/

            //删除造价企业重复未合并数据
            //deleteCostCompanyService.deleteTask();
        }
    }
}
