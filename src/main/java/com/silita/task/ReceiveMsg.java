package com.silita.task;

import com.silita.common.Constant;
import com.silita.common.kafka.KafkaConsumerBase;
import com.silita.common.redis.RedisUtils;
import com.silita.model.Company;
import com.silita.model.Document;
import com.silita.service.MohurdService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by dh on 2018/7/5.
 */
@Component
public class ReceiveMsg extends KafkaConsumerBase {
    private static Logger logger = Logger.getLogger(ReceiveMsg.class);

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private MohurdService mohurdService;

    public void init() {
        super.init();
    }

    @Override
    protected void msgHandle(Object msg) throws Exception {
        Document document = (Document) msg;
        logger.debug("开始接收消息..." + document.getObject().getClass());
        if (document.getObject() instanceof Company) {
            Company company = (Company) document.getObject();
            String com_id = company.getCom_id();
            String md5 = company.getMd5();
            boolean exists = redisUtils.hexists(Constant.REDIS_COMPANY, md5);
            if (!exists) {
                mohurdService.insertCompany(company);
                redisUtils.hset(Constant.REDIS_COMPANY, md5, com_id);
            } else {
                logger.info(String.format("=============%s 已存在！", md5));
            }
        }
    }
}
