package com.johnie.kafkademo.repository;

import com.johnie.kafkademo.entity.AllOrderStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AllOrderStatusRepo extends JpaRepository<AllOrderStatus, Long> {

    @Modifying
    @Query(value = "update all_order_status set orderastatus = :status where ref_order_no = :orderNo ", nativeQuery = true)
    void updateNodeAStatus(@Param("orderNo") String orderNo, @Param("status") int status);

    Optional<AllOrderStatus> findByRefOrderNo(String orderNo);
}
