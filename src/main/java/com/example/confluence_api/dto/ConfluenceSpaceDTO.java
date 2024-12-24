package com.example.confluence_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfluenceSpaceDTO 
{
    private String id;
    private String key;
    private String name;
    private String type;
    private String status;
    private String authorId;
    private String createdAt;
    private String homepageId;
    private ConfluenceSpaceIconDTO icon; 


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @JsonProperty("createdAt")
    public String createdAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getHomepageId() {
        return this.homepageId;
    }

    public void setHomepageId(String homepageId) {
        this.homepageId = homepageId;
    }

    public ConfluenceSpaceIconDTO getIcon() {
        return this.icon;
    }

    public void setIcon(ConfluenceSpaceIconDTO icon) {
        this.icon = icon;
    }

}
