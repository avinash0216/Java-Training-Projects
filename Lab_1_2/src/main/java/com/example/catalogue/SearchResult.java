// Result types using a sealed hierarchy
package com.example.catalogue;

import java.util.List;

public sealed interface SearchResult
        permits SearchResult.Success, SearchResult.Empty, SearchResult.InvalidRequest {

    record Success(List<ProductSummary> products, int totalCount) implements SearchResult {}
    record Empty(String reason) implements SearchResult {}
    record InvalidRequest(String validationError) implements SearchResult {}
}