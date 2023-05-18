package com.johnie.kafkademo.entity;

import com.johnie.kafkademo.enums.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class AllOrderStatus {
    @Id
    @GeneratedValue
    private Long id;
    private String refOrderNo;

    private OrderStatus orderAStatus;
    private OrderStatus orderBStatus;
    private OrderStatus orderCStatus;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }


    public OrderStatus getOrderAStatus() {
        return orderAStatus;
    }

    public void setOrderAStatus(OrderStatus orderAStatus) {
        this.orderAStatus = orderAStatus;
    }

    public OrderStatus getOrderBStatus() {
        return orderBStatus;
    }

    public void setOrderBStatus(OrderStatus orderBStatus) {
        this.orderBStatus = orderBStatus;
    }

    public OrderStatus getOrderCStatus() {
        return orderCStatus;
    }

    public void setOrderCStatus(OrderStatus orderCStatus) {
        this.orderCStatus = orderCStatus;
    }

    public String getRefOrderNo() {
        return refOrderNo;
    }

    public void setRefOrderNo(String refOrderNo) {
        this.refOrderNo = refOrderNo;
    }
}
