package com.inv.controller;


import com.inv.config.KafkaTopicProperties;
import com.inv.model.InventoryItem;
import com.inv.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryRepository inventoryRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTopicProperties kafkaTopicProperties;

    @Autowired
    public InventoryController(KafkaTemplate<String, String> kafkaTemplate,
                               KafkaTopicProperties kafkaTopicProperties, InventoryRepository inventoryRepository) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTopicProperties = kafkaTopicProperties;
        this.inventoryRepository = inventoryRepository;
    }
    

    @PostMapping("/sendOrderEvent")
    public String sendOrderEvent(@RequestBody String orderEventJson) {
        // Send order event JSON to Kafka topic
        kafkaTemplate.send(kafkaTopicProperties.getOrderCreated(), orderEventJson);
        return "Order event sent to topic: " + kafkaTopicProperties.getOrderCreated();
    }
    
    @PostMapping("/items")
    public InventoryItem addOrUpdateInventoryItem(@RequestBody InventoryItem inventoryItem) {
        return inventoryRepository.save(inventoryItem);
    }

    // Optional: Get inventory by productId
    @GetMapping("/items/{productId}")
    public InventoryItem getInventoryItem(@PathVariable String productId) {
        return inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Inventory item not found for productId: " + productId));
    }
}
