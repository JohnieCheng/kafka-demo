package com.johnie.kafkademo.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.johnie.kafkademo.event.DomainEvent;
import com.johnie.kafkademo.redis.ObjectRedisUtil;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;

import static com.johnie.kafkademo.kafka.KafkaUtil.MAX_MESSAGE_LENGTH;

@Component
public class KafkaDomainEventPublisher {
    Logger log = LoggerFactory.getLogger(KafkaDomainEventPublisher.class);
    @Autowired
    private KafkaTemplate<String, DomainEvent> kafkaTemplate;

    public void publish(String topic, DomainEvent event) {
        try {
            int size = new ObjectMapper().writeValueAsString(event).getBytes(StandardCharsets.UTF_8).length;
            if (size == 0) {
                return;
            }
            event.setContentSize(size);
            if (size > MAX_MESSAGE_LENGTH) {
                publishWithRedis(topic, event);
                return;
            }
            simplePublish(topic, event);
        } catch (JsonProcessingException e) {
            log.error("消息序列化异常:{}", e.getMessage());
            throw new RuntimeException("消息序列化异常");
        }
    }

    private void simplePublish(String topic, DomainEvent event) {
        Future<SendResult<String, DomainEvent>> future = kafkaTemplate.send(new ProducerRecord<>(topic, event));
        try {
            SendResult<String, DomainEvent> result = future.get();
            RecordMetadata recordMetadata = result.getRecordMetadata();
            if (recordMetadata != null) {
                log.info("发送一条消息,topic:{},message:{}", topic, event.getId());
            }
        } catch (Exception e) {
            log.error("发送消息失败,topic:{},message:{}", topic, event.getId());
            // 将发送失败的消息存入数据库，再通过异步任务重试，便于控制重试次数和间隔时间。
            // todo
            e.printStackTrace();
        }
    }

    private void publishWithRedis(String topic, DomainEvent event) {
        ObjectRedisUtil.set(event.getId(), event.getBody(), 5);
        event.setBody(null);
        this.simplePublish(topic, event);
        log.info("发送一条消息,topic:{},message:{}", topic, event.getId());
    }
}
