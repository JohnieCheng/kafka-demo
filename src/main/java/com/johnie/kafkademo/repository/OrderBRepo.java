package com.johnie.kafkademo.repository;

import com.johnie.kafkademo.entity.OrderA;
import com.johnie.kafkademo.entity.OrderB;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBRepo extends JpaRepository<OrderB, Long> {

}
