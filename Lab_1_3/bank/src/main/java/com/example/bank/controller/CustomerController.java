package com.example.bank.controller;

import com.example.bank.model.Account;
import com.example.bank.model.Customer;
import com.example.bank.service.AccountService;
import com.example.bank.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// @RestController combines @Controller and @ResponseBody.
// Every method return value is serialized directly to JSON.
// Without @ResponseBody, Spring MVC would treat the return value as a view name.
@RestController
// @RequestMapping sets the base path for all endpoints in this class.
// /api/v1 is the version prefix -- set at the class level so every
// method inherits it. No version logic goes inside a method body.
@RequestMapping("/api/v1/customers")
public class CustomerController {

    // Constructor injection is the only correct way to inject dependencies in Spring.
    // Spring instantiates this controller and injects the service at startup.
    // The field is final, which means this object cannot be created without it.
    private final CustomerService customerService;
    private final AccountService accountService;

    public CustomerController(CustomerService customerService, AccountService accountService) {
        this.customerService = customerService;
        this.accountService = accountService;
    }

    // GET /api/v1/customers
    @GetMapping
    public List<Customer> getAll() {
        return customerService.findAll();
    }

    // GET /api/v1/customers/{id}
    // @PathVariable binds the {id} placeholder in the URL to the method parameter.
    @GetMapping("/{id}")
    public Customer getById(@PathVariable String id) {
        return  customerService.findById(id);
    }

    @GetMapping("{id}/accounts")
    public List<Account> getAccountsById(@PathVariable String id){
        return customerService.findById(id).accountNumbers().stream()
                        .map(accountService::findByAccountNumber)
                        .toList();
    }
}