package com.example.bank.model;

import java.math.BigDecimal;

// Balance is BigDecimal, not double. Money must never be stored as a
// floating-point type because of representational error
// (0.1 + 0.2 != 0.3 in double). This is non-negotiable in a banking domain.
public record Account(
        String accountNumber,   // stable identifier, e.g. "ACC-1001"
        String customerId,      // foreign key to Customer.id
        AccountType type,
        AccountStatus status,
        BigDecimal balance
) {}