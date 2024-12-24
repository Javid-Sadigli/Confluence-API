package com.example.confluence_api.client.model;

public class ConfluenceContentResponse
{
    private String id;
    private String type;
    private String status;
    private String title;
    
    private ConfluenceContentBodyResponse body;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ConfluenceContentBodyResponse getBody() {
        return this.body;
    }

    public void setBody(ConfluenceContentBodyResponse body) {
        this.body = body;
    }

}