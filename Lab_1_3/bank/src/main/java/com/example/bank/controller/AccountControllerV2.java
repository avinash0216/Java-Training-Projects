package com.example.bank.controller;

import com.example.bank.model.AccountV2;
import com.example.bank.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// Version prefix changes to v2 at the class level.
// No version-branching logic inside any method.
// The version is an address, not a condition.
@RestController
@RequestMapping("/api/v2/accounts")
public class AccountControllerV2 {

    private final AccountService accountService;

    public AccountControllerV2(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountV2> getAll() {
        // TODO 16: Call accountService.findAll() and map each Account to AccountV2:
        //   - accountNumber, customerId, status, balance map directly
        //   - "type" -> "accountType"
        //   - availableBalance = balance (for the lab, they are the same number)
        //   - currency = "USD
        return accountService.findAll().stream()
                .map(a -> new AccountV2(
                        a.accountNumber(),
                        a.customerId(),
                        a.type(),
                        a.status(),
                        a.balance(),
                        a.balance(),     // availableBalance — same as balance for the lab
                        "USD"
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{accountNumber}")
    public AccountV2 getByAccountNumber(@PathVariable String accountNumber) {
        // TODO 17: Call accountService.findByAccountNumber(accountNumber) --
        // it throws AccountNotFoundException directly (after Exercise 4),
        // so no Optional handling is required.
        // Map the returned Account to AccountV2 the same way.

        var a = accountService.findByAccountNumber(accountNumber);
        return new AccountV2(
                a.accountNumber(),
                a.customerId(),
                a.type(),
                a.status(),
                a.balance(),
                a.balance(),
                "USD"
        );
    }
}