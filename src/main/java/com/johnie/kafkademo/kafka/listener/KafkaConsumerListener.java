package com.johnie.kafkademo.kafka.listener;


import com.johnie.kafkademo.entity.OrderA;
import com.johnie.kafkademo.entity.OrderB;
import com.johnie.kafkademo.enums.OrderStatus;
import com.johnie.kafkademo.kafka.config.KafkaTopicConstant;
import com.johnie.kafkademo.redis.ObjectRedisUtil;
import com.johnie.kafkademo.service.OrderAService;
import com.johnie.kafkademo.service.OrderBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerListener {

    @Autowired
    private OrderAService orderAService;
    @Autowired
    private OrderBService orderBService;

//    @KafkaListener(topics = "#{T(com.johnie.kafkademo.kafka.config.KafkaTopicConstant).ORDER_A}")
//    public void listenOnOrderA(String orderNo) {
//        OrderA orderA = ObjectRedisUtil.get(KafkaTopicConstant.UPDATE_NODE_A_STATUS + "$$" + orderNo);
//        orderA.setStatus(OrderStatus.STATUS_TWO);
//        orderAService.save(orderA);
//    }
//
//    @KafkaListener(topics = "#{T(com.johnie.kafkademo.kafka.config.KafkaTopicConstant).ORDER_A}")
//    public void listenOnOrderA_B(String orderNo) {
//        OrderA orderA = ObjectRedisUtil.get(KafkaTopicConstant.UPDATE_NODE_A_STATUS + "$$" + orderNo);
//        OrderB orderB = new OrderB();
//        orderB.setNo(orderA.getNo());
//        orderB.setStatus(OrderStatus.STATUS_ONE);
//        orderBService.save(orderB);
//    }
//
//    @KafkaListener(topics = "#{T(com.johnie.kafkademo.kafka.config.KafkaTopicConstant).ORDER_B}")
//    public void listenOnOrderB(String orderNo) {
//        OrderA orderA = ObjectRedisUtil.get(KafkaTopicConstant.UPDATE_NODE_A_STATUS + "$$" + orderNo);
//        OrderB orderB = new OrderB();
//        orderB.setNo(orderA.getNo());
//        orderB.setStatus(OrderStatus.STATUS_ONE);
//        orderBService.save(orderB);
//    }

}
