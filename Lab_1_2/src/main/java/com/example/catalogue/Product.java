// The domain entity held in the service's data store
package com.example.catalogue;

public record Product(
        String id,
        String name,
        double price,
        int stockLevel,
        String category,
        String status     // "ACTIVE", "DISCONTINUED", "OUT_OF_STOCK"
) {}