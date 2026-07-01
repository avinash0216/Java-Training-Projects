package com.example.bankclient.model;

public record CreditScore(
        String customerId,
        int score,
        String rating
) {}