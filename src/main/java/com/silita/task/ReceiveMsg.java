package com.silita.task;

import com.silita.common.kafka.KafkaConsumerBase;
import com.silita.factory.AbstractFactory;
import com.silita.factory.MohurdFactory;
import com.silita.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by dh on 2018/7/5.
 */
@Component
public class ReceiveMsg extends KafkaConsumerBase {
    private AbstractFactory factory;

    @Autowired
    private MohurdFactory mohurdFactory;

    @PostConstruct
    public void init() {
        this.factory = mohurdFactory;
        super.init();
    }

    @Override
    protected void msgHandle(Object msg) throws Exception {
        Document document = (Document) msg;
        factory.process(document.getObject());
    }
}
