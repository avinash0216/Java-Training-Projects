package com.example.order;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    // Calculate total for a list of items, optionally applying a premium discount
    public double calculateTotal(List<OrderItem> items, boolean isPremiumCustomer) {
        // Validate
        if (items == null) {
            throw new IllegalArgumentException("Order has no items");
        }
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Order has no items");
        }
        if (items.get(0).getProductId() == null) {
            throw new IllegalArgumentException("Order has no customer");
        }

        // Calculate total
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            total = total + (item.getQuantity() * item.getUnitPrice());
        }

        // Apply discount
        if (isPremiumCustomer == true) {
            total = total * 0.9;
        }

        // Apply free shipping threshold
        if (total >= 100.0) {
            // No shipping charge
        } else {
            total = total + 9.99;
        }

        return total;
    }

    public List<OrderItem> filterByMinValue(List<OrderItem> items, double minLineTotal) {
        List<OrderItem> result = new ArrayList<>();
        for (OrderItem item : items) {
            if (item.lineTotal() >= minLineTotal) {
                result.add(item);
            }
        }
        return result;
    }
}