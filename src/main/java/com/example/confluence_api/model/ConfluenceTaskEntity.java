package com.example.confluence_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "confluence_tasks")
public class ConfluenceTaskEntity 
{
    @Id
    private String id;

    private String localId;
    private String blogPostId;
    private String status;
    private String createdAt;
    private String updatedAt;
    private String dueAt;
    private String completedAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private ConfluenceUserEntity createdBy; 

    @ManyToOne
    @JoinColumn(name = "completed_by")
    private ConfluenceUserEntity completedBy; 

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private ConfluenceUserEntity assignedTo; 

    @ManyToOne
    @JoinColumn(name = "page_id")
    private ConfluenceContentEntity page;
    
    @ManyToOne
    @JoinColumn(name = "space_id")
    private ConfluenceSpaceEntity space;



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

    public String createdAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String updatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String dueAt() {
        return this.dueAt;
    }

    public void setDueAt(String dueAt) {
        this.dueAt = dueAt;
    }

    public String completedAt() {
        return this.completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public ConfluenceUserEntity createdBy() {
        return this.createdBy;
    }

    public void setCreatedBy(ConfluenceUserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public ConfluenceUserEntity completedBy() {
        return this.completedBy;
    }

    public void setCompletedBy(ConfluenceUserEntity completedBy) {
        this.completedBy = completedBy;
    }

    public ConfluenceUserEntity assignedTo() {
        return this.assignedTo;
    }

    public void setAssignedTo(ConfluenceUserEntity assignedTo) {
        this.assignedTo = assignedTo;
    }

    public ConfluenceContentEntity getPage() {
        return this.page;
    }

    public void setPage(ConfluenceContentEntity page) {
        this.page = page;
    }

    public ConfluenceSpaceEntity getSpace() {
        return this.space;
    }

    public void setSpace(ConfluenceSpaceEntity space) {
        this.space = space;
    }

}
