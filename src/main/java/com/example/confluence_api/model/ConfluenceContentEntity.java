package com.example.confluence_api.model;

import jakarta.persistence.*;

import java.util.List;

@Entity 
@Table(name = "confluence_contents")
public class ConfluenceContentEntity 
{
    @Id
    private String id;

    private String type;
    private String status;
    private String title;

    @OneToMany
    @JoinColumn(name = "page_id")
    private List<ConfluenceTaskEntity> tasks;

    @OneToMany
    @JoinColumn(name = "content_id")
    private List<ConfluenceContentVersionEntity> versions;


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

    public List<ConfluenceTaskEntity> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<ConfluenceTaskEntity> tasks) {
        this.tasks = tasks;
    }

    public List<ConfluenceContentVersionEntity> getVersions() {
        return this.versions;
    }

    public void setVersions(List<ConfluenceContentVersionEntity> versions) {
        this.versions = versions;
    }

}