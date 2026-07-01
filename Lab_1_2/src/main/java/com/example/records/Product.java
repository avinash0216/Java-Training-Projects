package com.example.records;

import java.util.stream.Stream;

public record Product(String id, String name, double price, int stockLevel) {

    public Product {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Product id must not be blank");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price must be non-negative, got: " + price);
        }
        if (stockLevel < 0) {
            throw new IllegalArgumentException("Stock level must be non-negative");
        }
        name = name == null ? "" : name.trim();
    }

    // "Wither" methods produce a new Product with one field changed.
    // All other fields are copied from the current instance.
    public Product withPrice(double newPrice) {
        return new Product(this.id, this.name, newPrice, this.stockLevel);
    }

    public Product withStockLevel(int newStockLevel) {
        return new Product(this.id, this.name, this.price, newStockLevel);
    }
//
//    public double processProduct(Product product){
//        return switch (product) {
//            case Product p when p.price() > 1000 -> p.price() * 0.9; // 10% discount for expensive products
//            case Product p when p.stockLevel() < 10 -> p.price() * 1.05; // 5% markup for low stock
//            default -> product.price(); // no change
//        };
//    }
}

