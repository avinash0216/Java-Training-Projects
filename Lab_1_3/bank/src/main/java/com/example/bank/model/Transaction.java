package com.example.bank.model;

import java.math.BigDecimal;
import java.time.Instant;

// Sealed: only the types listed in `permits` may implement this interface.
// All transactions share these four fields, exposed via the interface methods.
// A record's accessor methods automatically satisfy an interface's abstract
// methods of the same name, so each permitted record only has to declare
// its components -- no method bodies needed.
public sealed interface Transaction
        permits CreditTransaction, DebitTransaction {

    String transactionId();
    String accountNumber();
    BigDecimal amount();
    Instant timestamp();
}