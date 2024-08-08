package com.example.confluence_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "confluence_space_icons")
public class ConfluenceSpaceIconEntity 
{
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id; 

    public String path; 
    public String apiDownloadLink;
}
