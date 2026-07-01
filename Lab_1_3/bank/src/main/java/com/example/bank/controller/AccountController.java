package com.example.bank.controller;

import com.example.bank.model.Account;
import com.example.bank.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // GET /api/v1/accounts
    @GetMapping
    public List<Account> getAll() {
        return accountService.findAll();
    }

    // GET /api/v1/accounts/{accountNumber}
    @GetMapping("/{accountNumber}")
    public Account getByAccountNumber(@PathVariable String accountNumber) {
        // TODO 4: Same pattern as TODO 3 -- map/orElse using findByAccountNumber.
    return accountService.findByAccountNumber(accountNumber);
    }

    @ModelAttribute
    public void addDeprecationHeaders(HttpServletResponse response) {
        response.setHeader("Deprecation", "true");
        response.setHeader("Sunset", "Sat, 01 Nov 2026 00:00:00 GMT");
        response.setHeader("Link", "</api/v2/accounts>; rel=\"successor-version\"");
    }
}