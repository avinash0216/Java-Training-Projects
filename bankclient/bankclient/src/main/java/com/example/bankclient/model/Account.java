package com.example.bankclient.model;

import java.math.BigDecimal;

public record Account(
        String id,
        String customerId,
        String accountType,
        BigDecimal balance
) {}