package com.johnie.kafkademo.repository;

import com.johnie.kafkademo.entity.OrderA;
import com.johnie.kafkademo.entity.OrderC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCRepo extends JpaRepository<OrderC, Long> {

}
