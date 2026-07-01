package com.example.bank.exception;

public class AccountNotFoundException extends ResourceNotFoundException {
    public AccountNotFoundException(String accountNumber) {
        super("Account", accountNumber);
    }
}