package com.example.bank.controller;

import com.example.bank.model.Transaction;
import com.example.bank.model.TransactionType;
import com.example.bank.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // GET /api/v1/transactions
    // Top-level list of all transactions across all accounts.
    // In a real system this would be paginated and access-controlled;
    // for the lab it returns the full list.
    @GetMapping("/api/v1/transactions")
    public List<Transaction> getAll() {
        return transactionService.findAll();
    }

    // GET /api/v1/accounts/{accountNumber}/transactions
    // Sub-resource listing: just the transactions for one account.
    @GetMapping("/api/v1/accounts/{accountNumber}/transactions")
    public List<Transaction> getByAccount(@PathVariable String accountNumber) {
        return transactionService.findByAccountNumber(accountNumber);
    }

    // POST /api/v1/accounts/{accountNumber}/transactions
    // Create a transaction. The body specifies the action (CREDIT or DEBIT)
    // and the amount. The accountNumber comes from the URL, not the body --
    // the URL identifies the target.
    @PostMapping("/api/v1/accounts/{accountNumber}/transactions")
    public ResponseEntity<Transaction> create(
            @PathVariable String accountNumber,
            @RequestBody TransactionRequest request,
            UriComponentsBuilder ucb) {

        // TODO 9: Call transactionService.process(accountNumber, request.type(), request.amount()).
        // Build the Location URI pointing to the new transaction:
        //   /api/v1/transactions/{transactionId}
        // Return ResponseEntity.created(location).body(saved).
        // 201, not 200 -- 201 signals resource creation and carries the new resource's URI.

        var newTransaction = transactionService.process(accountNumber, request.type(), request.amount());
        URI location = ucb.path("/api/v1/transactions/{transactionId}")
                .buildAndExpand(newTransaction.transactionId())
                .toUri();
        return ResponseEntity.created(location).body(newTransaction);
    }

    // Request body record -- declared inside the controller because it is
    // a controller-layer concern (the wire contract). The service uses
    // primitives (TransactionType + BigDecimal), not this record.
    public record TransactionRequest(TransactionType type, BigDecimal amount) {}
}