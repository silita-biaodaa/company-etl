package com.silita.task;

import com.silita.common.kafka.KafkaConsumerBase;
import com.silita.model.Company;
import com.silita.model.Document;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by dh on 2018/7/5.
 */
@Component
public class ReceiveMsg extends KafkaConsumerBase {
    private static Logger logger = Logger.getLogger(ReceiveMsg.class);

    public void init(){
        super.init();
    }

    @Override
    protected void msgHandle(Object msg) throws Exception {
        Document document = (Document) msg;
        logger.debug("开始接收消息..."+document.getObject().getClass());
        if(document.getObject() instanceof Company){
            Company com =  (Company)document.getObject();
            logger.debug(com.toString());
        }

    }
}
