package com.example.confluence_api.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.confluence_api.api.response.ResultResponse;
import com.example.confluence_api.entity.ContentEntity;

public class ContentMapper 
{
    @Autowired private ExpandableMapper expandableMapper; 
    @Autowired private ExtensionsMapper extensionsMapper; 
    @Autowired private LinksMapper linksMapper; 

    public ContentMapper(
        ExpandableMapper expandableMapper, 
        ExtensionsMapper extensionsMapper, 
        LinksMapper linksMapper
    ){
        this.expandableMapper = expandableMapper; 
        this.extensionsMapper = extensionsMapper; 
        this.linksMapper = linksMapper; 
    }
    
    public ContentEntity responseToEntity(ResultResponse response)
    {
        try
        {
            ContentEntity contentEntity = new ContentEntity(); 
            contentEntity.id = response.id; 
            contentEntity.status = response.status; 
            contentEntity.title = response.title; 
            contentEntity.type = response.type; 
            contentEntity._expandable = this.expandableMapper.responseToEntity(response._expandable); 
            contentEntity._links = this.linksMapper.responseToEntity(response._links); 
            contentEntity.extensions = this.extensionsMapper.responseToEntity(response.extensions);
            return contentEntity; 
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }
}
