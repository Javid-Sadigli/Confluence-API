package com.example.confluence_api.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "confluence_groups")
public class ConfluenceGroupEntity 
{
    @Id
    private String id;

    private String type;
    private String name;

    @ManyToMany
    @JoinTable(
        name = "group_members", 
        joinColumns = @JoinColumn(name = "group_id"), 
        inverseJoinColumns = @JoinColumn(name = "user_account_id"
    )) private List<ConfluenceUserEntity> members;



    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ConfluenceUserEntity> getMembers() {
        return this.members;
    }

    public void setMembers(List<ConfluenceUserEntity> members) {
        this.members = members;
    }

}
