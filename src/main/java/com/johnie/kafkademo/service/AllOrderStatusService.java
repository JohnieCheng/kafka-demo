package com.johnie.kafkademo.service;

import com.johnie.kafkademo.enums.OrderStatus;

public interface AllOrderStatusService {
    void updateNodeAStatus(String orderNo, OrderStatus status);
}
