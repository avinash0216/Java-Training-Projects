package com.example.bankclient.controller;

import com.example.bankclient.model.Account;
import com.example.bankclient.service.AccountRestTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rt/accounts")
public class AccountRestTemplateController {

    private final AccountRestTemplateService service;

    public AccountRestTemplateController(AccountRestTemplateService service) {
        this.service = service;
    }

    @GetMapping
    public List<Account> getAll() {
        return service.fetchAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable String id) {
        var account = service.fetchById(id);
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
    }

    @GetMapping("/exchange")
    public List<Account> getAllWithExchange() {
        return service.fetchAllWithExchange();
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Account account) {
        var location = service.createAccount(account);
        return ResponseEntity.ok("Created at: " + location);
    }
}