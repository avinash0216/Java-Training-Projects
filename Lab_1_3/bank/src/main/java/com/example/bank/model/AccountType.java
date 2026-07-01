package com.example.bank.model;

// Two enums for fixed sets of values.
// Using enums instead of strings means a typo in code is a compile error,
// and IDEs can autocomplete the legal values.
public enum AccountType {
    CHECKING, SAVINGS
}