package com.silita.common.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.silita.common.disruptor.event.AnalyzeEvent;
import com.silita.model.Test;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DisruptorOperator {

    Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());


    private static EventTranslatorOneArg<AnalyzeEvent,Test> eventTranslator = new EventTranslatorOneArg<AnalyzeEvent,Test>() {
        @Override
        public void translateTo(AnalyzeEvent event, long sequence, Test test) {
            event.setTest(test);
        }
    };

    /**
     * 初始化disruptor
     */
    @PostConstruct
    public void init() {

    }

    /**
     * 启动disruptor
     */
    public void start() {
    }

    /**
     * 关闭disruptor
     */
    public void shutdown() {
    }

}
