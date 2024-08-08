package com.example.confluence_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class TaskEntity 
{
    @Id
    public String id;

    public String localId;
    public String blogPostId;
    public String status;
    public String createdAt;
    public String updatedAt;
    public String dueAt;
    public String completedAt;

    @ManyToOne
    @JoinColumn(name = "created_by")
    public UserEntity createdBy; 

    @ManyToOne
    @JoinColumn(name = "completed_by")
    public UserEntity completedBy; 

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    public UserEntity assignedTo; 

    @ManyToOne
    @JoinColumn(name = "page_id")
    public ContentEntity page;
    
    @ManyToOne
    @JoinColumn(name = "space_id")
    public SpaceEntity space;
}
