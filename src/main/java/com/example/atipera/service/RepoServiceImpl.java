package com.example.atipera.service;

import com.example.atipera.models.Branch;
import com.example.atipera.models.ResponseRepo;
import com.example.atipera.models.Repo;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RepoServiceImpl implements RepoService {

    public static final String BASE_URL = "https://api.github.com/";
    public List<ResponseRepo> getReposInfo(String username) {
        List<Repo> repos = getNonForkedRepos(username);

        List<ResponseRepo> responseRepos = repos.stream()
                .map(repo -> {
                    String name = repo.name();
                    String ownerLogin = repo.owner().login();
                    Branch[] branches = getBranches(username, repo.name()).toArray(Branch[]::new);
                    ResponseRepo responseRepo = new ResponseRepo(name, ownerLogin, branches);
                    return responseRepo;
                }).collect(Collectors.toList());

        return responseRepos;
    }

    public List<Repo> getNonForkedRepos(String username) {
        String url = BASE_URL + "users/" + username + "/repos";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Repo[]> response = restTemplate.getForEntity(url, Repo[].class);
        List<Repo> repositories = Arrays.asList(response.getBody());

        return repositories.stream().filter(repo -> !repo.fork()).toList();
    }

    public List<Branch> getBranches(String username, String repo) {
        String url = BASE_URL + "repos/" + username + "/" + repo + "/branches";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Branch[]> response = restTemplate.getForEntity(url, Branch[].class);

        return Arrays.asList(response.getBody());
    }
}
