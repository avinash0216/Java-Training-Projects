package com.example.streams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDemo {

    static List<Order> sampleOrders() {
        return List.of(
                new Order("O001", "C01", "ELECTRONICS", 1200.00, "COMPLETED"),
                new Order("O002", "C02", "CLOTHING",    85.50,   "COMPLETED"),
                new Order("O003", "C01", "ELECTRONICS", 340.00,  "PENDING"),
                new Order("O004", "C03", "BOOKS",       22.99,   "CANCELLED"),
                new Order("O005", "C02", "BOOKS",       34.00,   "COMPLETED"),
                new Order("O006", "C03", "ELECTRONICS", 2500.00, "COMPLETED"),
                new Order("O007", "C01", "CLOTHING",    112.00,  "PENDING"),
                new Order("O008", "C04", "ELECTRONICS", 899.99,  "COMPLETED")
        );
    }

    public static void main(String[] args) {
        List<Order> orders = sampleOrders();

        // TODO 10: Produce a list of order IDs for all COMPLETED orders
        // whose totalAmount exceeds 500.00.
        // Use a stream pipeline: filter -> map -> collect
        List<String> highValueCompleted = orders.stream()
                .filter(order -> order.status().equals("COMPLETED") && order.totalAmount() > 500.00)
                .map(Order::orderId)
                .toList();

        System.out.println("High-value completed order IDs: " + highValueCompleted);
        // Expected: [O001, O006, O008]

        printStats(orders);
        grouping(orders);
        cartDemo();
    }

    private static void printStats(List<Order> orders){
        // TODO 11: Using streams, calculate:
        //   a) The total revenue from COMPLETED orders (sum of totalAmount)
        //   b) The number of PENDING orders
        //   c) The average order value across ALL orders

        // Hint for (a): look at mapToDouble(...).sum()
        // Hint for (b): filter then count()
        // Hint for (c): mapToDouble(...).average() returns an OptionalDouble

        double totalRevenue = orders.stream()
                .filter(order -> order.status().equals("COMPLETED"))
                /* your pipeline here */
                .mapToDouble(Order::totalAmount)
                .sum(); // remove or adapt as needed

        long pendingCount = 0; // TODO: replace with a stream pipeline
        pendingCount = orders.stream()
                .filter(order -> order.status().equals("PENDING"))
                .count();

        OptionalDouble averageOrderValue = OptionalDouble.empty(); // TODO: replace
        averageOrderValue = orders.stream().filter(order -> order.totalAmount() > 0)
                .mapToDouble(Order::totalAmount)
                .average();

        System.out.printf("Total revenue (COMPLETED): $%.2f%n", totalRevenue);
        System.out.println("Pending order count: " + pendingCount);
        averageOrderValue.ifPresent(avg -> System.out.printf("Average order value: $%.2f%n", avg));
    }

    private static void grouping(List<Order> orders){

        // TODO 12: Group orders by category.
        // The result should be a Map<String, List<Order>>.
        // Then print each category and the number of orders in it.

        Map<String, List<Order>> byCategory = orders.stream()
                .collect(Collectors.groupingBy(Order::category));

        Map<String, Integer> orderCountByCategory = orders.stream()
                .collect(Collectors.groupingBy(Order::category, Collectors.summingInt(order -> 1)));

        byCategory.forEach((category, categoryOrders) ->
                System.out.println(category + ": " + categoryOrders.size() + " order(s)"));

        Map<String, Double> revenuesbyCategory = orders.stream()
                .collect(Collectors.groupingBy(Order::category, Collectors.summingDouble(Order::totalAmount)));

        revenuesbyCategory.forEach((c,r) -> System.out.printf("%-15s $%.2f%n", c,r));

        Stream<String> lst = orders.stream().map(Order::category);

         orders.stream()
                .map(Order::category)
                .forEach((c) -> System.out.printf("%-15s %n",c));

    }

    private static void cartDemo(){
        // In StreamDemo.main():

        List<Cart> carts = List.of(
                new Cart("CART-1", "C01", List.of("SKU-A", "SKU-B", "SKU-C")),
                new Cart("CART-2", "C02", List.of("SKU-B", "SKU-D")),
                new Cart("CART-3", "C03", List.of("SKU-A", "SKU-E"))
        );

        // TODO 14: Produce a flat list of ALL item SKUs across all carts.
        // Then find the distinct SKUs.
        // Hint: use flatMap(cart -> cart.itemSkus().stream())

        List<String> allSkus = carts.stream()
                .flatMap(cart -> cart.itemSkus().stream())
                .toList();

        List<String> distinctSkus = carts.stream()
                .flatMap(cart -> cart.itemSkus().stream())
                .distinct()
                .sorted()
                .toList();

                /* .flatMap(...) */
                /* .distinct() */
                /* .sorted() */
                /* .collect(...) */
                ;

        System.out.println("All SKUs (with duplicates): " + allSkus);
        System.out.println("Distinct SKUs sorted: " + distinctSkus);
// Expected distinct: [SKU-A, SKU-B, SKU-C, SKU-D, SKU-E]
    }
}