package com.johnie.kafkademo.kafka.listener;

import com.johnie.kafkademo.entity.OrderA;
import com.johnie.kafkademo.enums.OrderStatus;
import com.johnie.kafkademo.event.DomainEvent;
import com.johnie.kafkademo.kafka.config.KafkaTopicConstant;
import com.johnie.kafkademo.redis.ObjectRedisUtil;
import com.johnie.kafkademo.service.AllOrderStatusService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UpdateNodeStatusAConsumer {

    @Autowired
    private AllOrderStatusService allOrderStatusService;

    @KafkaListener(topics = "#{T(com.johnie.kafkademo.kafka.config.KafkaTopicConstant).UPDATE_NODE_A_STATUS}", containerFactory = "kafkaListenerContainerFactory")
    public void listenOnOrderA(String orderNo) {
        OrderA orderA = ObjectRedisUtil.get(orderNo + "$$" + KafkaTopicConstant.UPDATE_NODE_A_STATUS);
        // 更新消息逻辑
        OrderStatus status = OrderStatus.STATUS_TWO;
        // 持久化
        allOrderStatusService.updateNodeAStatus(orderNo, status);
    }

    @KafkaListener(topics = "#{T(com.johnie.kafkademo.kafka.config.KafkaTopicConstant).UPDATE_NODE_B_STATUS}", containerFactory = "kafkaListenerContainerFactory2")
    public void listenOnOrderA(DomainEvent event) {
        OrderA orderA = ObjectRedisUtil.get(event.getEventId());
        if (event.getEventType().contains("add")) {
            // 判断状态逻辑
            OrderStatus status = OrderStatus.STATUS_TWO;
            allOrderStatusService.updateNodeAStatus(orderA.getNo(), status);
        }
        if (event.getEventType().contains("update")) {
            // 判断状态逻辑
            OrderStatus status = OrderStatus.STATUS_THREE;
            allOrderStatusService.updateNodeAStatus(orderA.getNo(), status);
        }
        if (event.getEventType().contains("delete")) {
            // 判断状态逻辑
            OrderStatus status = OrderStatus.STATUS_ONE;
            allOrderStatusService.updateNodeAStatus(orderA.getNo(), status);
        }
    }
}
