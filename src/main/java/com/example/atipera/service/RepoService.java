package com.example.atipera.service;

import com.example.atipera.models.ResponseRepo;
import com.example.atipera.models.Branch;
import com.example.atipera.models.Repo;

import java.util.List;

public interface RepoService {
    List<ResponseRepo> getReposInfo(String username);

    List<Repo> getNonForkedRepos(String username);

    List<Branch> getBranches(String username, String repo);
}
