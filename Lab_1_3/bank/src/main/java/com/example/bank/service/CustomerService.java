package com.example.bank.service;

import com.example.bank.exception.ResourceNotFoundException;
import com.example.bank.model.Customer;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

// @Service marks this class as a Spring-managed bean in the service layer.
// It is functionally identical to @Component but communicates intent:
// this class contains business logic, not HTTP handling or data access.
@Service
public class CustomerService {

    // In-memory store keyed by customer ID.
    // ConcurrentHashMap is used here because Tomcat is multithreaded and may
    // handle concurrent requests. This is not a concern you would manage manually
    // with a real database.
    private final Map<String, Customer> store = new ConcurrentHashMap<>();

    public CustomerService() {
        // Seed data -- customers and their account numbers.
        // The accounts themselves are seeded in AccountService and must
        // use the same account numbers listed here.
        store.put("C001", new Customer("C001", "Alice Nguyen",
                List.of("ACC-1001", "ACC-1002")));
        store.put("C002", new Customer("C002", "Bob Patel",
                List.of("ACC-1003")));
        store.put("C003", new Customer("C003", "Carla Romero",
                List.of("ACC-1004", "ACC-1005")));
    }

    public List<Customer> findAll() {
        return List.copyOf(store.values());
    }

    public Customer findById(String id) {
        if (null == store.get(id)) {
            throw new ResourceNotFoundException("Customer", id);
        }
        return store.get(id);
    }
}