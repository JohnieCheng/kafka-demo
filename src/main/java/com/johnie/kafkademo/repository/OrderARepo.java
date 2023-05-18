package com.johnie.kafkademo.repository;

import com.johnie.kafkademo.entity.OrderA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderARepo extends JpaRepository<OrderA, Long> {

    Optional<OrderA> findByNo(String no);
}
