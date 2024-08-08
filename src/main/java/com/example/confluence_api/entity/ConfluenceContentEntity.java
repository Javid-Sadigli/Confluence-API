package com.example.confluence_api.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity 
@Table(name = "confluence_contents")
public class ConfluenceContentEntity 
{
    @Id
    public String id;

    public String type;
    public String status;
    public String title;

    @OneToMany
    @JoinColumn(name = "page_id")
    public List<ConfluenceTaskEntity> tasks;
}
