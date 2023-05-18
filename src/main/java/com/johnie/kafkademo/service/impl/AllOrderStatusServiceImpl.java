package com.johnie.kafkademo.service.impl;

import com.johnie.kafkademo.entity.AllOrderStatus;
import com.johnie.kafkademo.enums.OrderStatus;
import com.johnie.kafkademo.repository.AllOrderStatusRepo;
import com.johnie.kafkademo.service.AllOrderStatusService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class AllOrderStatusServiceImpl implements AllOrderStatusService {
    @Autowired
    private AllOrderStatusRepo orderStatusRepo;

    @Override
    public void updateNodeAStatus(String orderNo, OrderStatus status) {
        Optional<AllOrderStatus> orderStatusOptional = orderStatusRepo.findByRefOrderNo(orderNo);
        if (orderStatusOptional.isPresent()) {
            orderStatusOptional.get().setOrderAStatus(status);
            return;
        }
        initAllOrderStatus(orderNo, status);
    }

    private void initAllOrderStatus(String orderNo, OrderStatus status) {
        AllOrderStatus allOrderStatus = new AllOrderStatus();
        allOrderStatus.setRefOrderNo(orderNo);
        allOrderStatus.setOrderAStatus(status);
        orderStatusRepo.save(allOrderStatus);
    }
}
