package com.ord.controller;


import com.ord.messaging.OrderProducer;
import com.ord.model.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody OrderEvent orderEvent) {
        orderProducer.sendOrderEvent(orderEvent);
        return ResponseEntity.ok("Order event sent.");
    }
}

