package com.example.confluence_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfluenceTaskDTO 
{
    private String id;
    private String localId;
    private String spaceId;
    private String pageId;
    private String blogPostId;
    private String status;
    private String createdBy;
    private String assignedTo;
    private String completedBy;
    private String createdAt;
    private String updatedAt;
    private String dueAt;
    private String completedAt;


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalId() {
        return this.localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getSpaceId() {
        return this.spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getPageId() {
        return this.pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public String getBlogPostId() {
        return this.blogPostId;
    }

    public void setBlogPostId(String blogPostId) {
        this.blogPostId = blogPostId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("createdBy")
    public String createdBy() {
        return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @JsonProperty("assignedTo")
    public String assignedTo() {
        return this.assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @JsonProperty("completedBy")
    public String completedBy() {
        return this.completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    @JsonProperty("createdAt")
    public String createdAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updatedAt")
    public String updatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("dueAt")
    public String dueAt() {
        return this.dueAt;
    }

    public void setDueAt(String dueAt) {
        this.dueAt = dueAt;
    }

    @JsonProperty("completedAt")
    public String completedAt() {
        return this.completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

}