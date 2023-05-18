package com.johnie.kafkademo.service.impl;

import com.johnie.kafkademo.entity.OrderB;
import com.johnie.kafkademo.repository.OrderBRepo;
import com.johnie.kafkademo.service.OrderBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderBServiceImpl implements OrderBService {
    @Autowired
    private OrderBRepo orderBRepo;

    @Override
    public OrderB save(OrderB orderB) {
        return orderBRepo.save(orderB);
    }
}
