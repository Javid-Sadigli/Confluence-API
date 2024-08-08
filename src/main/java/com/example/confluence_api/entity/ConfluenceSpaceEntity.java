package com.example.confluence_api.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "confluence_spaces")
public class ConfluenceSpaceEntity 
{
    @Id
    public String id;

    public String key;
    public String name;
    public String type;
    public String status;
    public String createdAt;
    
    @ManyToOne
    @JoinColumn(name = "icon_id")
    public ConfluenceSpaceIconEntity icon; 

    @ManyToOne
    @JoinColumn(name = "author_id")
    public ConfluenceUserEntity author; 

    @OneToOne
    @JoinColumn(name = "homepage_id")
    public ConfluenceContentEntity homepage;

    @OneToMany
    @JoinColumn(name = "space_id")
    public List<ConfluenceContentEntity> pages; 
}
