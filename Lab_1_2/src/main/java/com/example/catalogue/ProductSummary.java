// Summary DTO returned in search results
package com.example.catalogue;

public record ProductSummary(String id, String name, double price, String category) {}