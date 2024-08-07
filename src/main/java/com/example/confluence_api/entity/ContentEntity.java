package com.example.confluence_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "confluence_content")
public class ContentEntity 
{
    @Id
    public String id;

    public String type;
    public String status;
    public String title;
}
