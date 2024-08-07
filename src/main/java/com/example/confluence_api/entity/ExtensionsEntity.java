package com.example.confluence_api.entity;

// import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "extensions")
public class ExtensionsEntity 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    
    public int position;    
}
