package com.inv.messaging;



import com.inv.model.InventoryItem;
import com.inv.model.OrderEvent;
import com.inv.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class InventoryConsumer {

    @Autowired
    private InventoryRepository inventoryRepository;

    @KafkaListener(topics = "order-created-topic", groupId = "inventory-service-group")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        System.out.println("Consumed order event: " + orderEvent);

        InventoryItem item = inventoryRepository.findById(orderEvent.getProductId())
                .orElseGet(() -> {
                    InventoryItem newItem = new InventoryItem();
                    newItem.setProductId(orderEvent.getProductId());
                    newItem.setStockQuantity(100);  // Initial stock
                    return newItem;
                });

        int updatedQuantity = item.getStockQuantity() - orderEvent.getQuantity();
        item.setStockQuantity(Math.max(updatedQuantity, 0));

        inventoryRepository.save(item);

        System.out.println("Updated inventory for product: " + item.getProductId() + ", new stock: " + item.getStockQuantity());
    }
}
