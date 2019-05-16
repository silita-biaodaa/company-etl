package com.silita.service;

import com.silita.consumer.RedisUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IAptitudeCleanServiceTest extends ConfigTest {

    @Autowired
    private IAptitudeCleanService aptitudeCleanService;

    @Test
    public void cleanAual() {

        aptitudeCleanService.splitCompanyAptitudeByCompanyId("7a06fcda9354bf85a3867626fdc62ad2");
        aptitudeCleanService.updateCompanyAptitude("7a06fcda9354bf85a3867626fdc62ad2");

    }


}
