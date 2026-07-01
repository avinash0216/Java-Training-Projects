package com.example.bankclient.service;

import com.example.bankclient.model.Account;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class AccountRestTemplateService {

    private final RestTemplate restTemplate;

    public AccountRestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // TODO 2: Implement fetchAll() using getForObject.
    // The upstream URL is "/api/accounts".
    // The return type must be Account[].class (arrays are safe for getForObject).
    // Convert the array to a List before returning.
    // Hint: Arrays.asList(restTemplate.getForObject(...))
    public List<Account> fetchAll() {
        //return List.of(); // Replace with your implementation
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject("/api/accounts", Account[].class)));
    }

    // TODO 3: Implement fetchById() using getForEntity.
    // The upstream URL uses a URI variable: "/api/accounts/{id}".
    // Return the body from the ResponseEntity.
    // Inspect the status code: if it is not 2xx, return null.
    // Hint: restTemplate.getForEntity("/api/accounts/{id}", Account.class, id)
    public Account fetchById(String id) {
        //return null; // Replace with your implementation
        return restTemplate.getForEntity("/api/accounts/{id}", Account.class, id)
                .getStatusCode()
                .is2xxSuccessful() ? restTemplate.getForEntity("/api/accounts/{id}", Account.class, id).getBody() : null;
    }

    // TODO 4: Implement fetchAllWithExchange() using exchange.
    // This method does the same job as fetchAll() but uses ParameterizedTypeReference
    // so the response is deserialized directly into List<Account> rather than Account[].
    // Hint:
    //   var typeRef = new ParameterizedTypeReference<List<Account>>() {};
    //   ResponseEntity<List<Account>> response = restTemplate.exchange(
    //       "/api/accounts", HttpMethod.GET, HttpEntity.EMPTY, typeRef);
    //   return response.getBody();
    public List<Account> fetchAllWithExchange() {
        //return List.of(); // Replace with your implementation
        return restTemplate.exchange("/api/accounts", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<Account>>() {})
                .getBody();
    }

    // TODO 5: Implement createAccount() using postForEntity.
    // POST to "/api/accounts" with the account object as the request body.
    // Return the Location header from the response as a String.
    // Hint: restTemplate.postForEntity("/api/accounts", account, Account.class)
    //       response.getHeaders().getLocation().toString()
    public String createAccount(Account account) {
        //return null; // Replace with your implementation
        return Objects.requireNonNull(restTemplate.postForEntity("/api/accounts", account, Account.class)
                        .getHeaders()
                        .getLocation())
                .toString();
    }
}