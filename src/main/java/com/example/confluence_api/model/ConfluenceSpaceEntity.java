package com.example.confluence_api.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "confluence_spaces")
public class ConfluenceSpaceEntity 
{
    @Id
    private String id;

    private String key;
    private String name;
    private String type;
    private String status;
    private String createdAt;
    
    @ManyToOne
    @JoinColumn(name = "icon_id")
    private ConfluenceSpaceIconEntity icon; 

    @ManyToOne
    @JoinColumn(name = "author_id")
    private ConfluenceUserEntity author; 

    @OneToOne
    @JoinColumn(name = "homepage_id")
    private ConfluenceContentEntity homepage;

    @OneToMany
    @JoinColumn(name = "space_id")
    private List<ConfluenceContentEntity> pages; 



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

    public String createdAt() {
        return this.createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ConfluenceSpaceIconEntity getIcon() {
        return this.icon;
    }

    public void setIcon(ConfluenceSpaceIconEntity icon) {
        this.icon = icon;
    }

    public ConfluenceUserEntity getAuthor() {
        return this.author;
    }

    public void setAuthor(ConfluenceUserEntity author) {
        this.author = author;
    }

    public ConfluenceContentEntity getHomepage() {
        return this.homepage;
    }

    public void setHomepage(ConfluenceContentEntity homepage) {
        this.homepage = homepage;
    }

    public List<ConfluenceContentEntity> getPages() {
        return this.pages;
    }

    public void setPages(List<ConfluenceContentEntity> pages) {
        this.pages = pages;
    }

}
