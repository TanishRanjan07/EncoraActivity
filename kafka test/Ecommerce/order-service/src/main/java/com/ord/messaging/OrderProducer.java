package com.ord.messaging;


import com.ord.model.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final String TOPIC = "order-created-topic";

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderEvent(OrderEvent orderEvent) {
        kafkaTemplate.send(TOPIC, orderEvent.getOrderId(), orderEvent);
        System.out.println("Order event sent to topic: " + TOPIC);
    }
}
