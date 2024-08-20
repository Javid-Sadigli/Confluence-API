package com.example.confluence_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "confluence_space_icons")
public class ConfluenceSpaceIconEntity 
{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

    private String path; 
    private String apiDownloadLink;


    public int getId() {
        return this.id;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getApiDownloadLink() {
        return this.apiDownloadLink;
    }

    public void setApiDownloadLink(String apiDownloadLink) {
        this.apiDownloadLink = apiDownloadLink;
    }

}