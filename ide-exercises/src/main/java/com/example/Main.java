// src/main/java/com/example/Main.java
package com.example;

import com.example.order.OrderItem;
import com.example.order.OrderService;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var service = new OrderService();
        var items = List.of(
                new OrderItem("SKU-A", 3, 25.00),
                new OrderItem("SKU-B", 1, 9.99)
        );
        double total = service.calculateTotal(items, false, null);
        System.out.println("Order total: $" + total);

        var filtered = service.itemsAboveMinLineTotal(items, 20.0);
        System.out.println("Items above threshold: " + filtered.size());
    }
}