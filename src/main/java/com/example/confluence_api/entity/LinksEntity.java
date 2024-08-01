package com.example.confluence_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "links")
public class LinksEntity 
{
    @Id
    public Long id;
    
    public String self;
    public String tinyui;
    public String editui;
    public String webui;
    public String base;
    public String context;    
}
