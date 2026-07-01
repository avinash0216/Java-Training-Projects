package com.example.bankclient.controller;

import com.example.bankclient.model.Account;
import com.example.bankclient.service.AccountWebClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wc/accounts")
public class AccountWebClientController {

    private final AccountWebClientService service;

    public AccountWebClientController(AccountWebClientService service) {
        this.service = service;
    }

    @GetMapping
    public List<Account> getAll() {
        return service.fetchAll();
    }

    @GetMapping("/{id}")
    public Account getById(@PathVariable String id) {
        return service.fetchById(id);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Account account) {
        var location = service.createAccount(account);
        return ResponseEntity.ok("Created at: " + location);
    }
}