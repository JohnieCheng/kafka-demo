package com.johnie.kafkademo.kafka.listener;

import com.johnie.kafkademo.entity.OrderA;
import com.johnie.kafkademo.enums.OrderStatus;
import com.johnie.kafkademo.event.DomainEvent;
import com.johnie.kafkademo.kafka.config.ConsumerGroup;
import com.johnie.kafkademo.kafka.config.KafkaTopicConstant;
import com.johnie.kafkademo.redis.ObjectRedisUtil;
import com.johnie.kafkademo.service.AllOrderStatusService;
import jakarta.transaction.Transactional;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.johnie.kafkademo.kafka.KafkaUtil.getEventBody;

@Service
@Transactional
public class UpdateNodeStatusAConsumer {

    @Autowired
    private AllOrderStatusService allOrderStatusService;

    Logger log = LoggerFactory.getLogger(UpdateNodeStatusAConsumer.class);

    @KafkaListener(topics = "#{T(com.johnie.kafkademo.kafka.config.KafkaTopicConstant).ADD_ORDER_A}",
            containerFactory = "kafkaListenerContainerFactory2",
            groupId = ConsumerGroup.UPDATE_NODE_A_STATUS)
    public void listenOnAddOrderA(ConsumerRecord<String, DomainEvent> records, Acknowledgment ack) {
        DomainEvent event = records.value();

        /*
         * 预防重复消费的逻辑
         * 1、获取当前msg的 offset，与固定字符串拼接成key
         * 2、去redis中查询，如果不存在，即可消费。如果存在，即消费过，需要提交ack，并跳过消费逻辑
         * 3、在正常消费时，需要将当前key缓存到redis，并设置好过期时间2天，用于下面的消息验证
         * 4、2天过期时间是为了能够给一些异常预留处理的时间。比如程序down机
         */
        long offset = records.offset();
        String key = KafkaTopicConstant.ADD_ORDER_A + "&&" + ConsumerGroup.UPDATE_NODE_A_STATUS + "_" + offset;
        Object cacheObject = ObjectRedisUtil.get(key);
        if (null != cacheObject) {
            log.warn("==== 接收到kafka消息，topic:{}, offset:{}, partition号：{} ，tenantId:{}====",
                    records.topic(), offset, records.partition(), event.getTenantId());
            log.warn("====   重复消费！！！！！  ======");
            ack.acknowledge();
        }

        // 更新消息逻辑
        Object obj = getEventBody(event);
        OrderA orderA = (OrderA) obj;
        OrderStatus status = orderA.getStatus();
        // 持久化
        allOrderStatusService.updateNodeAStatus(orderA.getNo(), status);

        //消费完毕将值缓存到redis 进行验证，避免重复消费
        ObjectRedisUtil.set(key, offset, 2, TimeUnit.DAYS);
        // 手动提交
        ack.acknowledge();
    }

    @KafkaListener(topics = "#{T(com.johnie.kafkademo.kafka.config.KafkaTopicConstant).UPDATE_ORDER_A}",
            containerFactory = "kafkaListenerContainerFactory2",
            groupId = ConsumerGroup.UPDATE_NODE_A_STATUS)
    public void listenOnUpdateOrderA(ConsumerRecord<String, DomainEvent> records, Acknowledgment ack) {
        DomainEvent event = records.value();

        long offset = records.offset();
        String key = KafkaTopicConstant.UPDATE_ORDER_A + "&&" + ConsumerGroup.UPDATE_NODE_A_STATUS + "_" + offset;
        Object cacheObject = ObjectRedisUtil.get(key);
        if (null != cacheObject) {
            log.warn("==== 接收到kafka消息，topic:{}, offset:{}, partition号：{} ，tenantId:{}====",
                    records.topic(), offset, records.partition(), event.getTenantId());
            log.warn("====   重复消费！！！！！  ======");
            ack.acknowledge();
        }

        Object obj = getEventBody(event);
        OrderA orderA = (OrderA) obj;
        // 更新消息逻辑
        OrderStatus status = orderA.getStatus();
        // 持久化
        allOrderStatusService.updateNodeAStatus(orderA.getNo(), status);

        ObjectRedisUtil.set(key, offset, 2, TimeUnit.DAYS);
        ack.acknowledge();
    }
}
