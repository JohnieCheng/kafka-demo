package com.johnie.kafkademo.kafka.config;

import jakarta.transaction.Transactional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class KafkaErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @DltHandler
    public void dltHandler(ConsumerRecord<String, String> record, Acknowledgment ack,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                           @Header(KafkaHeaders.OFFSET) long offset) {
        logger.error("topic:{}, key:{}, value:{}", record.topic(), record.key(), record.value());
        // 发短信通知公司相关人员
        ack.acknowledge();
    }
}
