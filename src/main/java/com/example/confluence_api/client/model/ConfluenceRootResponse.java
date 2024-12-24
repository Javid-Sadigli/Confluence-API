package com.example.confluence_api.client.model;

import java.util.ArrayList;

public class ConfluenceRootResponse<T> 
{
    private ArrayList<T> results;
    private int start;
    private int limit;
    private int size;


    public ArrayList<T> getResults() {
        return this.results;
    }

    public void setResults(ArrayList<T> results) {
        this.results = results;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    
}