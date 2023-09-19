package com.example.atipera.service;

import com.example.atipera.models.ResponseRepo;
import com.example.atipera.models.Branch;
import com.example.atipera.models.Repo;
import reactor.core.publisher.Flux;

import java.util.List;

public interface RepoService {
    Flux<ResponseRepo> getReposInfo(String username);

}
