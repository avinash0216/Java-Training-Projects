package com.example.bank.service;

import com.example.bank.exception.AccountNotFoundException;
import com.example.bank.exception.BusinessRuleException;
import com.example.bank.model.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final Map<String, Transaction> store = new ConcurrentHashMap<>();
    private final AccountService accountService;

    // Constructor injection -- AccountService is a Spring bean and Spring
    // wires it in automatically.
    public TransactionService(AccountService accountService) {
        this.accountService = accountService;
    }

    public List<Transaction> findAll() {
        return List.copyOf(store.values());
    }

    public List<Transaction> findByAccountNumber(String accountNumber) {
        return store.values().stream()
                .filter(t -> t.accountNumber().equals(accountNumber))
                .collect(Collectors.toList());
    }

    /**
     * Process a credit or debit against an existing account.
     * - Verifies the account exists (404 via exception handler if not).
     * - Verifies the account is OPEN (422 via exception handler if not).
     * - Computes the new balance.
     * - For debits, verifies sufficient funds (422 if not).
     * - Persists the updated account and the transaction record.
     */
    public Transaction process(String accountNumber, TransactionType type, BigDecimal amount) {

        Account account = accountService.findByAccountNumber(accountNumber);

        if (account.status() != AccountStatus.OPEN) {
            throw new BusinessRuleException("ACCOUNT_NOT_OPEN",
                    "Cannot post a transaction to a closed account: " + accountNumber);
        }
        if (amount.signum() <= 0) {
            throw new BusinessRuleException("INVALID_AMOUNT",
                    "Transaction amount must be greater than zero");
        }

        // TODO 7: Compute the new balance based on the transaction type.
        //   - For TransactionType.CREDIT, newBalance = account.balance() + amount
        //   - For TransactionType.DEBIT,  newBalance = account.balance() - amount,
        //     but only if account.balance() >= amount. If insufficient funds,
        //     throw new BusinessRuleException("INSUFFICIENT_FUNDS", ...).
        // Use BigDecimal.add and BigDecimal.subtract -- do not convert to double.
        BigDecimal newBalance;
        // your code here

        newBalance = switch (type) {
            case CREDIT -> account.balance().add(amount);
            case DEBIT -> {
                if (account.balance().compareTo(amount) < 0) {
                    throw new BusinessRuleException("INSUFFICIENT_FUNDS",
                            "Cannot debit " + amount + " from account " + accountNumber
                                    + " with balance " + account.balance());
                }
                yield account.balance().subtract(amount);
            }
        };

        // Write back the updated account record (creates a new Account value
        // because Account is immutable -- see AccountService.updateBalance).
        accountService.updateBalance(accountNumber, newBalance);

        // TODO 8: Construct the appropriate Transaction record.
        // Use a switch expression on `type`:
        //   - CREDIT -> new CreditTransaction(id, accountNumber, amount, Instant.now())
        //   - DEBIT  -> new DebitTransaction (id, accountNumber, amount, Instant.now())
        // Generate the id with: "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase()
        // The switch must be exhaustive -- because TransactionType is an enum, the
        // compiler will reject a non-exhaustive switch.
        String txnId = "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Transaction txn;
        txn = switch (type){
            case CREDIT -> new CreditTransaction(txnId, accountNumber, amount, Instant.now());
            case DEBIT -> new DebitTransaction(txnId, accountNumber, amount, Instant.now());
        };

        store.put(txn.transactionId(), txn);
        return txn;
    }
}