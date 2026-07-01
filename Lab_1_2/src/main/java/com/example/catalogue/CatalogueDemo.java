package com.example.catalogue;

import java.util.stream.Collectors;

public class CatalogueDemo {

    public static void main(String[] args) {
        var service = new ProductCatalogueService();

        var result1 = service.search(new ProductSearchRequest("ELECTRONICS", 50.00, 300.00, "ACTIVE"));
        System.out.println("Test 1: " + describeResult(result1));

        var result2 = service.search(new ProductSearchRequest("ELECTRONICS", 500.00, 100.00, null));
        System.out.println("Test 2: " + describeResult(result2));

        var result3 = service.search(new ProductSearchRequest("FURNITURE", 1000.00, 5000.00, "ACTIVE"));
        System.out.println("Test 3: " + describeResult(result3));
    }

    private static String describeResult(SearchResult result) {
        if (result instanceof SearchResult.Success s) {
            return "Found " + s.totalCount() + " product(s): " +
                    s.products().stream().map(ProductSummary::name).collect(Collectors.joining(", "));

        } else if (result instanceof SearchResult.Empty e) {
            return "No results: " + e.reason();

        } else if (result instanceof SearchResult.InvalidRequest i) {
            return "Bad request: " + i.validationError();

        } else {
            throw new IllegalStateException("Unhandled SearchResult type: " + result.getClass());
        }
    }
}