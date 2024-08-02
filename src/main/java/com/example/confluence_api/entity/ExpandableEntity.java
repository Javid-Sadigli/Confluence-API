package com.example.confluence_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "expandable")
public class ExpandableEntity 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id; 
    
    public String container;
    public String metadata;
    public String restrictions;
    public String history;
    public String body;
    public String version;
    public String descendants;
    public String space;
    public String childTypes;
    public String schedulePublishInfo;
    public String operations;
    public String schedulePublishDate;
    public String children;
    public String ancestors;
}
