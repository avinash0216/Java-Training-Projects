package com.example.bankclient.service;

import com.example.bankclient.exception.AccountNotFoundException;
import com.example.bankclient.model.Account;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AccountWebClientService {

    private final WebClient accountWebClient;

    public AccountWebClientService(WebClient accountWebClient) {
        this.accountWebClient = accountWebClient;
    }

    // TODO 9: Implement fetchAll() using WebClient.
    // Chain: .get().uri("/api/accounts").retrieve()
    //        .bodyToFlux(Account.class).collectList().block()
    // bodyToFlux turns the JSON array into a reactive stream of Account objects.
    // collectList() gathers all emitted items into a single Mono<List<Account>>.
    // block() subscribes and waits synchronously (acceptable in Spring MVC).
    public List<Account> fetchAll() {
        return accountWebClient.get()
                .uri("/api/accounts")
                .retrieve()
                .bodyToFlux(Account.class)
                .collectList()
                .block();
    }

    // TODO 10: Implement fetchById() using URI template variables.
    // Chain: .get().uri("/api/accounts/{id}", id).retrieve()
    //        .bodyToMono(Account.class).block()
    // URI template variables are automatically percent-encoded.
    // They are resistant to path traversal because {id} is treated as a single segment.
    public Account fetchById(String id) {
    // TODO 12: Replace the fetchById() implementation with one that maps a 404
    // to AccountNotFoundException using onStatus().
    //
       return accountWebClient.get()
           .uri("/api/accounts/{id}", id)
           .retrieve()
           .onStatus(
               status -> status.value() == 404,
               response -> Mono.error(new AccountNotFoundException(id)))
           .bodyToMono(Account.class)
           .block();
    //
    // onStatus() intercepts responses matching the predicate and substitutes
    // the supplied error signal instead of attempting to deserialize the body.
    // Add the required import: import reactor.core.publisher.Mono;
    }

    // TODO 11: Implement createAccount() using POST.
    // Chain: .post().uri("/api/accounts")
    //        .bodyValue(account)
    //        .retrieve()
    //        .toBodilessEntity()     <- returns Mono<ResponseEntity<Void>>
    //        .block()
    // Extract the Location header: response.getHeaders().getLocation()
    // Return the Location URI as a String.
    public String createAccount(Account account) {
        //return null; // Replace with your implementation

        var response = accountWebClient.post()
                .uri("/api/accounts")
                .bodyValue(account)
                .retrieve()
                .toBodilessEntity()
                .block();
        var location = response.getHeaders().getLocation();
        return location.toString();
    }
}