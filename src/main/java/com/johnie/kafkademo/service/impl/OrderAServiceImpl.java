package com.johnie.kafkademo.service.impl;

import com.johnie.kafkademo.entity.OrderA;
import com.johnie.kafkademo.enums.OrderStatus;
import com.johnie.kafkademo.event.DomainEvent;
import com.johnie.kafkademo.kafka.KafkaDomainEventPublisher;
import com.johnie.kafkademo.kafka.config.KafkaTopicConstant;
import com.johnie.kafkademo.repository.OrderARepo;
import com.johnie.kafkademo.service.OrderAService;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderAServiceImpl implements OrderAService {

    @Autowired
    private OrderARepo orderARepo;
//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;
//    @Autowired
//    private KafkaTemplate<String, DomainEvent> kafkaTemplate2;

    @Autowired
    private KafkaDomainEventPublisher kafkaDomainEventPublisher;

    @Override
    public OrderA add(OrderA orderA) {
        OrderA save = save(orderA);
        List<OrderA> orderAs= new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            orderAs.add(orderA);
        }
        kafkaDomainEventPublisher.publish(KafkaTopicConstant.ADD_ORDER_A,
                new DomainEvent("add_order_a", orderAs));
        return save;
    }

    @Override
    public OrderA update(OrderA orderA) {
        if (orderARepo.findById(orderA.getId()).isPresent()) {
            orderA.setStatus(OrderStatus.STATUS_TWO);
            orderA.setLastUpdateTime(new Date());
            AtomicInteger atomicInteger = new AtomicInteger(orderA.getUpdateTimes());
            int andIncrement = atomicInteger.addAndGet(1);
            orderA.setUpdateTimes(andIncrement);
            OrderA save = save(orderA);
            kafkaDomainEventPublisher.publish(KafkaTopicConstant.UPDATE_ORDER_A,
                    new DomainEvent("update_order_a", save));
            return save;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<OrderA> orderAOptional = orderARepo.findById(id);
        if (orderAOptional.isPresent()) {
//            OrderA orderA = orderAOptional.get();
//            DateTimeFormatter formatter = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss").toFormatter();
//            String key = orderA.getNo() + "$" + LocalDateTime.now().format(formatter);
//            ObjectRedisUtil.set(key, orderA, 30);
//
//            UpdateNodeAStatusEvent domainEvent = new UpdateNodeAStatusEvent("johnie", "删除orderA");
//            domainEvent.setEventId(key);
//            domainEvent.setEventType("delete-order-a");
//
//            kafkaTemplate2.send(KafkaTopicConstant.UPDATE_NODE_B_STATUS, domainEvent);
//            orderARepo.delete(orderA);
        }

    }

    @Override
    public OrderA findById(Long id) {
        return orderARepo.findById(id).orElse(null);
    }

    @Override
    public OrderA save(OrderA orderA) {
        return orderARepo.save(orderA);
    }
}
