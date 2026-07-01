package com.example.bank.model;

import java.math.BigDecimal;

// v2 response shape. A separate record, not a modified Account.
// v1 and v2 coexist independently.
public record AccountV2(
        String accountNumber,
        String customerId,
        AccountType accountType,        // renamed from "type" -- the breaking change
        AccountStatus status,
        BigDecimal balance,
        BigDecimal availableBalance,    // new field
        String currency                 // new field
) {}