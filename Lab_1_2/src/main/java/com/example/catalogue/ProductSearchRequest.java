// Request DTO
package com.example.catalogue;

public record ProductSearchRequest(
        String category,
        double minPrice,
        double maxPrice,
        String status      // "ACTIVE", "DISCONTINUED", "OUT_OF_STOCK"
) {}