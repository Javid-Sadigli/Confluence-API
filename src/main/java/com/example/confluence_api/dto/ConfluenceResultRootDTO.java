package com.example.confluence_api.dto;

import org.springframework.http.HttpStatus;

public class ConfluenceResultRootDTO <T> 
{
    private T result;
    private String message; 
    private HttpStatus status; 


    public T getResult() {
        return this.result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
    
}
