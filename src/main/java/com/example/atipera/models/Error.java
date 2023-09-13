package com.example.atipera.models;

import org.springframework.http.HttpStatusCode;

public record Error(HttpStatusCode status, String message) {
}
