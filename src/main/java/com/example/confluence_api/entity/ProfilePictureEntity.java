package com.example.confluence_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "profile_pictures")
public class ProfilePictureEntity 
{   
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id; 

    public String path; 
    public int width;
    public int height;
    public boolean isDefault; 
}
