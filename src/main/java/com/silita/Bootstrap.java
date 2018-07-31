package com.silita;

import com.silita.service.IAptitudeCleanService;
import com.silita.task.ReceiveMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements ApplicationListener<ApplicationEvent> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IAptitudeCleanService aptitudeCleanService;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            //aptitudeCleanService.splitAllCompanyAptitude();
            //aptitudeCleanService.updateAllCompanyAptitude();
        }
    }
}
