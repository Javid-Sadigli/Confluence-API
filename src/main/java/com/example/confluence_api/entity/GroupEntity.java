package com.example.confluence_api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "groups")
public class GroupEntity 
{
    @Id
    public String id;

    public String type;
    public String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "_links_id")
    public LinksEntity _links;    
}
