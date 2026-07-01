package com.example.bank.model;

import java.math.BigDecimal;
import java.time.Instant;

// A debit decreases the account balance (withdrawal).
public record DebitTransaction(
        String transactionId,
        String accountNumber,
        BigDecimal amount,
        Instant timestamp
) implements Transaction {}