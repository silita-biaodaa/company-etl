package com.silita.task;

import com.silita.factory.MohurdFactory;
import com.silita.model.Document;
import com.silita.utils.DocumentDecoder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
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
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:chenzhiqiang
 * @Date:2018/7/31 19:39
 * @Description: kafka消费者
 */
@Configuration
@EnableKafka
public class KafkaConsumer {
    @Autowired
    private MohurdFactory factory;

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
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, DocumentDecoder.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "groupA");
        return props;
    }

    /**
     * 获取工厂
     */
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    /**
     * 获取实例
     */
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(1);//设置并发数
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }

    /**
     * 监听器获取消息
     *
     * @param record
     */
    @KafkaListener(topics = {"com_etl_queue"})
    public void getSpiderMessage(ConsumerRecord<?, ?> record) {
        Document document = (Document) record.value();
        if (null != document && null != document.getObject()) {
            factory.process(document.getObject());
        }
    }
}
