package com.silita.consumer;

import com.alibaba.fastjson.JSONObject;
import com.silita.factory.HighwayFactory;
import com.silita.factory.MohurdFactory;
import com.silita.factory.ShuiliFactory;
import com.silita.factory.SkyChongqFactory;
import com.silita.spider.common.serializable.Document;
import com.silita.utils.DocumentDecoder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.Acknowledgment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:chenzhiqiang
 * @Date:2018/7/31 19:39
 * @Description: kafka消费者
 */
@Configuration
@EnableKafka
public class KafkaConsumer {

    private static Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Autowired
    private MohurdFactory mohurdFactory;
    @Autowired
    private HighwayFactory highwayFactory;
    @Autowired
    private ShuiliFactory shuiliFactory;
    @Autowired
    private SkyChongqFactory skyChongqFactory;

    @Value("${kafka.bootstrap.servers}")
    private String servers;

    /**
     * 获取配置
     */
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DocumentDecoder.class);
        return props;
    }

    /**
     * 获取配置
     */
    public Map<String, Object> consumerStringConfigs() {
        Map<String, Object> props = new HashMap<>(8);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000 * 60 * 3);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    /**
     * 获取工厂
     */
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /**
     * 获取工厂
     */
    public ConsumerFactory<String, String> consumerStrFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerStringConfigs());
    }

    /**
     * 获取实例
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        //设置并发数
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(6000 * 10);
        factory.setBatchListener(true);
        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    /**
     * 获取实例
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerStrFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerStrFactory());
        //设置并发数
        factory.setConcurrency(3);
        factory.getContainerProperties().setPollTimeout(6000);
        factory.setBatchListener(true);
        factory.getContainerProperties().setAckMode(AbstractMessageListenerContainer.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }

    /**
     * 监听器获取消息
     * 全国四库一站点
     *
     * @param records
     */
//    @KafkaListener(topics = "com_etl_queue", containerFactory = "kafkaListenerContainerFactory", groupId = "groupA")
    public void getSiKuYiSpiderMessage(List<ConsumerRecord<?, ?>> records, Acknowledgment acknowledgment) {
        try {
            for (ConsumerRecord<?, ?> record : records) {
                Document document = (Document) record.value();
                if (null != document && null != document.getObject()) {
                    mohurdFactory.process(document.getObject());
                    acknowledgment.acknowledge();
                }
            }
        } catch (Exception e) {
            logger.warn("消费sky数据失败！！！", e);
        }
    }

    /**
     * 监听器获取消息
     * 公路
     *
     * @param records
     */
    @KafkaListener(topics = "highway", containerFactory = "kafkaListenerContainerStrFactory", groupId = "test-consumer-group")
    public void getHighwayRecords(List<ConsumerRecord<?, ?>> records, Acknowledgment acknowledgment) {
        for (ConsumerRecord<?, ?> record : records) {
            process(record, highwayFactory, acknowledgment);
        }
    }

    /**
     * 监听器获取消息
     * 水利
     *
     * @param records
     */
    @KafkaListener(topics = "shuili", containerFactory = "kafkaListenerContainerStrFactory", groupId = "test-consumer-group")
    public void getShuiliRecords(List<ConsumerRecord<?, ?>> records, Acknowledgment acknowledgment) {
        for (ConsumerRecord<?, ?> record : records) {
            process(record, shuiliFactory, acknowledgment);
        }
    }


    /**
     * 监听器获取消息
     * 公路
     *
     * @param records
     */
//    @KafkaListener(topics = "chongq", containerFactory = "kafkaListenerContainerStrFactory", groupId = "test-consumer-group")
    public void getChongqRecords(List<ConsumerRecord<?, ?>> records, Acknowledgment acknowledgment) {
        for (ConsumerRecord<?, ?> record : records) {
            this.process(record, skyChongqFactory, acknowledgment);
        }
    }

    private void process(ConsumerRecord<?, ?> record, Object object, Acknowledgment acknowledgment) {
        try {
            if (null != record.value()) {
                JSONObject jsonObject = JSONObject.parseObject(record.value().toString());
                if (null != jsonObject) {
                    if (object instanceof HighwayFactory) {
                        highwayFactory.process(jsonObject);
                        acknowledgment.acknowledge();
                    } else if (object instanceof ShuiliFactory) {
                        shuiliFactory.process(jsonObject);
                        acknowledgment.acknowledge();
                    } else if (object instanceof SkyChongqFactory) {
                        skyChongqFactory.process(jsonObject);
                        acknowledgment.acknowledge();
                    }
                }
            }
        } catch (Exception e) {
            logger.error("消费数据失败！！！", e);
        }
    }

}
