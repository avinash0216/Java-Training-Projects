package com.example.bankclient.model;

public record Customer(
        String id,
        String fullName,
        String email
) {}