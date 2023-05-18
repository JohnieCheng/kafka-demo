package com.johnie.kafkademo.entity;

import com.johnie.kafkademo.enums.OrderStatus;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class OrderC implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String no;
    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}