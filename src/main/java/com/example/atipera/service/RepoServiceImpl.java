package com.example.atipera.service;

import com.example.atipera.models.Branch;
import com.example.atipera.models.ResponseRepo;
import com.example.atipera.models.Repo;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepoServiceImpl implements RepoService {

    private final WebClient webClient;

    public RepoServiceImpl(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("https://api.github.com").build();
    }

    public Flux<ResponseRepo> getReposInfo(String username) {
        return getRepos(username)
                .flatMap(repo -> {
                    String name = repo.name();
                        String ownerLogin = repo.owner().login();
                        Mono<Branch[]> branches = getBranches(username, repo.name());
                        return branches.map(branchesValue -> new ResponseRepo(name, ownerLogin, branchesValue));
                });
    }
    private Flux<Repo> getRepos(String username) {
        return webClient
                .get()
                .uri("/users/" + username + "/repos")
                .retrieve()
                .bodyToFlux(Repo.class)
                .filter(repo -> !repo.fork());
    }

    private Mono<Branch[]> getBranches(String username, String repo) {
        return webClient
                .get()
                .uri("/repos/" + username + "/" + repo + "/branches")
                .retrieve()
                .bodyToMono(Branch[].class);
    }
}