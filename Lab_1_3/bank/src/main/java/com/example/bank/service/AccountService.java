package com.example.bank.service;

import com.example.bank.exception.AccountNotFoundException;
import com.example.bank.model.Account;
import com.example.bank.model.AccountStatus;
import com.example.bank.model.AccountType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AccountService {

    private final Map<String, Account> store = new ConcurrentHashMap<>();

    public AccountService() {
        // Seeded accounts. The account numbers and customerIds match
        // the references in CustomerService.
        save(new Account("ACC-1001", "C001", AccountType.CHECKING,
                AccountStatus.OPEN, new BigDecimal("2500.00")));
        save(new Account("ACC-1002", "C001", AccountType.SAVINGS,
                AccountStatus.OPEN, new BigDecimal("15000.00")));
        save(new Account("ACC-1003", "C002", AccountType.CHECKING,
                AccountStatus.OPEN, new BigDecimal("780.50")));
        save(new Account("ACC-1004", "C003", AccountType.CHECKING,
                AccountStatus.OPEN, new BigDecimal("3200.00")));
        save(new Account("ACC-1005", "C003", AccountType.SAVINGS,
                AccountStatus.CLOSED, new BigDecimal("0.00")));
    }

    public List<Account> findAll() {
        return List.copyOf(store.values());
    }

    public Account findByAccountNumber(String accountNumber) {
        if(null== store.get(accountNumber)){
            throw new AccountNotFoundException(accountNumber);
        }
        return store.get(accountNumber);
    }

    // Internal helper -- not exposed via the controller.
    // Used during seeding and by the transaction-processing logic in Exercise 3
    // to write back an updated account record after the balance changes.
    Account save(Account account) {
        store.put(account.accountNumber(), account);
        return account;
    }

    // TODO 2: Implement updateBalance(String accountNumber, BigDecimal newBalance).
    // Because Account is an immutable record, you cannot mutate the balance.
    // Instead:
    //   1. Look up the existing account by number.
    //   2. If absent, return Optional.empty().
    //   3. If present, construct a NEW Account record with the same
    //      accountNumber, customerId, type, and status, but the new balance.
    //   4. Put the new record into the store, replacing the old one.
    //   5. Return Optional.of(updatedAccount).
    public Optional<Account> updateBalance(String accountNumber, BigDecimal newBalance) {

        var existingAccount = store.values()
                .stream()
                .filter(a->a.accountNumber().equals(accountNumber))
                .findFirst();

        return existingAccount.map(old -> {
            Account updated = new Account(
                    old.accountNumber(),
                    old.customerId(),
                    old.type(),
                    old.status(),
                    newBalance
            );
            save(updated);
            return updated;
        });
    }
}