package com.example.streams;

public record Order(
        String orderId,
        String customerId,
        String category,
        double totalAmount,
        String status   // "COMPLETED", "PENDING", "CANCELLED"
) {}