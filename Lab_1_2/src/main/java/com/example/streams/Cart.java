package com.example.streams;

import java.util.List;

public record Cart(String cartId, String customerId, List<String> itemSkus) {}