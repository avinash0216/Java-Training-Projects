package com.example.catalogue;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductCatalogueService {

    private final List<Product> products = List.of(
            new Product("P001", "Laptop Pro",        1299.99, 42,  "ELECTRONICS", "ACTIVE"),
            new Product("P002", "USB-C Hub",           45.00, 200, "ELECTRONICS", "ACTIVE"),
            new Product("P003", "Wireless Headset",   249.99,  0,  "ELECTRONICS", "OUT_OF_STOCK"),
            new Product("P004", "Desk Chair",         399.00, 15,  "FURNITURE",   "ACTIVE"),
            new Product("P005", "Monitor Stand",       89.99, 75,  "FURNITURE",   "ACTIVE"),
            new Product("P006", "Mechanical Keyboard", 129.99, 30, "ELECTRONICS", "ACTIVE"),
            new Product("P007", "Legacy Printer",      59.99,  5,  "ELECTRONICS", "DISCONTINUED")
    );

    public SearchResult search(ProductSearchRequest request) {
        Optional<String> validationError = validate(request);
        if (validationError.isPresent()) {
            return new SearchResult.InvalidRequest(validationError.get());
        }

        List<ProductSummary> matches = products.stream()
                .filter(p -> p.category().equalsIgnoreCase(request.category()))
                .filter(p -> p.price() >= request.minPrice() && p.price() <= request.maxPrice())
                .filter(p -> request.status() == null || request.status().isBlank()
                        || p.status().equalsIgnoreCase(request.status()))
                .map(p -> new ProductSummary(p.id(), p.name(), p.price(), p.category()))
                .collect(Collectors.toList());

        if (matches.isEmpty()) {
            return new SearchResult.Empty(
                    "No products found in category '" + request.category() +
                            "' within price range $" + request.minPrice() + " - $" + request.maxPrice());
        }

        return new SearchResult.Success(matches, matches.size());
    }

    private Optional<String> validate(ProductSearchRequest request) {
        if (request.category() == null || request.category().isBlank()) {
            return Optional.of("category must not be blank");
        }
        if (request.minPrice() < 0) {
            return Optional.of("minPrice must be >= 0");
        }
        if (request.maxPrice() <= request.minPrice()) {
            return Optional.of("maxPrice must be greater than minPrice");
        }
        return Optional.empty();
    }
}