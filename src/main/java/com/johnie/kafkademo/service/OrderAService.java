package com.johnie.kafkademo.service;

import com.johnie.kafkademo.entity.OrderA;

public interface OrderAService {
    OrderA add(OrderA orderA);

    OrderA save(OrderA orderA);

    OrderA update(OrderA orderA);

    void delete(Long id);
}
