package com.example.confluence_api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "extensions_id")
    public ExtensionsEntity extensions;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "_expandable_id")
    public ExpandableEntity _expandable;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "_links_id")
    public LinksEntity _links;    
}
