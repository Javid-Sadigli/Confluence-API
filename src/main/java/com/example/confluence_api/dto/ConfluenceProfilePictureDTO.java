package com.example.confluence_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfluenceProfilePictureDTO 
{
    private String path; 
    private int width;
    private int height;
    private boolean isDefault; 


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

    @JsonProperty("isDefault")
    public boolean isDefault() {
        return this.isDefault;
    }

    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

}
