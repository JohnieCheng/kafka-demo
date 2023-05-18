package com.johnie.kafkademo.service.impl;

import com.johnie.kafkademo.event.DomainEvent;
import com.johnie.kafkademo.entity.OrderA;
import com.johnie.kafkademo.event.UpdateNodeAStatusEvent;
import com.johnie.kafkademo.kafka.config.KafkaTopicConstant;
import com.johnie.kafkademo.redis.ObjectRedisUtil;
import com.johnie.kafkademo.repository.OrderARepo;
import com.johnie.kafkademo.service.OrderAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Optional;

@Service
public class OrderAServiceImpl implements OrderAService {

    @Autowired
    private OrderARepo orderARepo;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private KafkaTemplate<String, DomainEvent> kafkaTemplate2;

    @Override
    public OrderA add(OrderA orderA) {
        ObjectRedisUtil.set(orderA.getNo() + "$$" + KafkaTopicConstant.UPDATE_NODE_A_STATUS, orderA, 30);
        kafkaTemplate.send(KafkaTopicConstant.UPDATE_NODE_A_STATUS, orderA.getNo());
        return save(orderA);
    }

    @Override
    public OrderA update(OrderA orderA) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter();
        String key = orderA.getNo() + "$" + LocalDateTime.now().format(formatter);
        ObjectRedisUtil.set(key, orderA, 30);

        UpdateNodeAStatusEvent domainEvent = new UpdateNodeAStatusEvent("johnie", "更新orderA");
        domainEvent.setEventId(key);
        domainEvent.setEventType("update-order-a");

        kafkaTemplate2.send(KafkaTopicConstant.UPDATE_NODE_B_STATUS, domainEvent);
        return save(orderA);
    }

    @Override
    public void delete(Long id) {
        Optional<OrderA> orderAOptional = orderARepo.findById(id);
        if (orderAOptional.isPresent()) {
            OrderA orderA = orderAOptional.get();
            DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter();
            String key = orderA.getNo() + "$" + LocalDateTime.now().format(formatter);
            ObjectRedisUtil.set(key, orderA, 30);

            UpdateNodeAStatusEvent domainEvent = new UpdateNodeAStatusEvent("johnie", "删除orderA");
            domainEvent.setEventId(key);
            domainEvent.setEventType("delete-order-a");

            kafkaTemplate2.send(KafkaTopicConstant.UPDATE_NODE_B_STATUS, domainEvent);
            orderARepo.delete(orderA);
        }

    }

    @Override
    public OrderA save(OrderA orderA) {
        return orderARepo.save(orderA);
    }
}
