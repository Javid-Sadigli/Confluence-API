package com.example.confluence_api.dto;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;

public class ConfluenceResultsRootDTO <T> 
{
    private ArrayList<T> results;
    private int pageNumber;
    private int size;
    private String message; 
    private HttpStatus status; 


    public ArrayList<T> getResults() {
        return this.results;
    }

    public void setResults(ArrayList<T> results) {
        this.results = results;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
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
