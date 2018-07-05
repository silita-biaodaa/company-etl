package com.silita.biaodaa.task;

import com.silita.biaodaa.common.kafka.KafkaConsumerBase;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by dh on 2018/7/5.
 */
@Component
public class ReceiveMsg extends KafkaConsumerBase{
    private static Logger logger = Logger.getLogger(ReceiveMsg.class);

    public void init(){
        super.init();
    }

    @Override
    protected void msgHandle(Object msg) throws Exception {
        logger.debug("开始接收消息..."+msg.toString());
    }
}
