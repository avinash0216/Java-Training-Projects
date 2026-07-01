package com.example.bank.model;

import java.util.List;

// A customer owns one or more accounts. The customer's id is generated
// server-side; in this lab it is set during seeding.
public record Customer(
        String id,
        String name,
        List<String> accountNumbers  // references to Account.accountNumber, not Account objects
) {}