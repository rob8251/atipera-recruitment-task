package com.example.atipera.models;

import org.springframework.http.HttpStatusCode;

public class Error {

    private HttpStatusCode status;
    private String message;

    public Error(HttpStatusCode status, String message) {
        this.status = status;
        this.message = message;
    }

    public Error() {}
    public HttpStatusCode getStatus() {
        return status;
    }

    public void setStatus(HttpStatusCode status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
