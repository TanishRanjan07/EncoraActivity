package com.inv.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class InventoryItem {

    @Id
    private String productId;
    private int stockQuantity;
}
