package com.example.confluence_api.client.model;

public class ConfluencePageRootResponse<T> 
{
    private ConfluenceRootResponse<T> page;    
    

    public ConfluenceRootResponse<T> getPage() {
        return this.page;
    }

    public void setPage(ConfluenceRootResponse<T> page) {
        this.page = page;
    }
    
}
