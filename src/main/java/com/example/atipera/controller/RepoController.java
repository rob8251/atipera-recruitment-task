package com.example.atipera.controller;

import com.example.atipera.models.Error;
import com.example.atipera.models.ResponseRepo;
import com.example.atipera.service.RepoServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class RepoController {

    private final RepoServiceImpl repoService;
    private final ObjectMapper objectMapper;
    public RepoController(RepoServiceImpl repoService, ObjectMapper objectMapper) {
        this.repoService = repoService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/api/v1/{username}")
    public ResponseEntity<Flux<ResponseRepo>> getReposByUsername(@PathVariable("username") String username) {

        Flux<ResponseRepo> responseRepos = repoService.getReposInfo(username);
        return ResponseEntity.status(HttpStatus.OK).body(responseRepos);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNotFound(HttpClientErrorException.NotFound e) throws JsonProcessingException {
        Error error = new Error(e.getStatusCode(), "User not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(objectMapper.writeValueAsString(error));
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInvalidAcceptHeader(HttpMediaTypeNotAcceptableException e) throws JsonProcessingException {
        Error error = new Error(e.getStatusCode(), "Type not acceptable");
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(objectMapper.writeValueAsString(error));
    }

}
