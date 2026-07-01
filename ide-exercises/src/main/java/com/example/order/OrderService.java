package com.example.order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService {

    public static final double PREMIUM_DISCOUNT_RATE = 0.9;
    public static final double STANDARD_SHIPPING_FEE = 9.99;
    public static final double FREE_SHIPPING_THRESHOLD = 100.0;

    // Calculate total for a list of items, optionally applying a premium discount
    public double calculateTotal(List<OrderItem> items, boolean isPremiumCustomer, String note) {
        // Validate
        validateItems(items);

        // Calculate total
        double total = 0;
        total = subtotal(items, total);

        // Apply discount
        if (isPremiumCustomer) {
            total = total * PREMIUM_DISCOUNT_RATE;
        }

        // Apply free shipping threshold
        total = shippingFee(total);

        return total;
    }

    private static double shippingFee(double total) {
        if (total < FREE_SHIPPING_THRESHOLD) {
            total = total + STANDARD_SHIPPING_FEE;
        }
        return total;
    }

    private static double subtotal(List<OrderItem> items, double total) {
        for (OrderItem item : items) {
            total = total + (item.getQuantity() * item.getUnitPrice());
        }
        return total;
    }

    private static void validateItems(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order has no items");
        }
        for (OrderItem item : items) {
            if (item == null) {
                throw new IllegalArgumentException("Order contains null item");
            }
            if (item.getProductId() == null || item.getProductId().isBlank()) {
                throw new IllegalArgumentException("Order contains item with missing productId");
            }
            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException("Item quantity must be positive");
            }
            if (item.getUnitPrice() < 0) {
                throw new IllegalArgumentException("Item unit price cannot be negative");
            }
        }
    }

    public List<OrderItem> itemsAboveMinLineTotal(List<OrderItem> items, double minLineTotal) {
        return items.stream()
                .filter(item -> item.lineTotal() >= minLineTotal)
                .collect(Collectors.toList());
    }
}


record Point22(int x, int y){}