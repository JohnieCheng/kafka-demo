package com.johnie.kafkademo.controller;

import com.johnie.kafkademo.entity.OrderA;
import com.johnie.kafkademo.service.OrderAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-a")
public class OrderAController {

    @Autowired
    private OrderAService orderAService;

    @PostMapping()
    public OrderA add(@RequestBody OrderA orderA) {
        return orderAService.add(orderA);
    }

    @PutMapping()
    public OrderA update(@RequestBody OrderA orderA) {
        return orderAService.update(orderA);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        orderAService.delete(id);
    }

}
