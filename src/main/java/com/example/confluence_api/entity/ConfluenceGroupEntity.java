package com.example.confluence_api.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "confluence_groups")
public class ConfluenceGroupEntity 
{
    @Id
    public String id;

    public String type;
    public String name;

    @ManyToMany
    @JoinTable(
        name = "group_members", 
        joinColumns = @JoinColumn(name = "group_id"), 
        inverseJoinColumns = @JoinColumn(name = "user_account_id"
    )) public List<ConfluenceUserEntity> members;
}
