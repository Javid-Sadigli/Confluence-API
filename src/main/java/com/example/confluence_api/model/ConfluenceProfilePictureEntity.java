package com.example.confluence_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "confluence_profile_pictures")
public class ConfluenceProfilePictureEntity 
{   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; 

    private String path; 
    private int width;
    private int height;
    private boolean isDefault; 



    public int getId() {
        return this.id;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isDefault() {
        return this.isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

}