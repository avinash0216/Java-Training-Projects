package com.example.bank.model;

import java.math.BigDecimal;
import java.time.Instant;

// A credit increases the account balance (deposit, from the bank's perspective).
public record CreditTransaction(
        String transactionId,
        String accountNumber,
        BigDecimal amount,
        Instant timestamp
) implements Transaction {}