package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.ContentResponse;
import com.example.confluence_api.dto.ContentDTO;
import com.example.confluence_api.entity.ContentEntity;

@Component
public class ContentMapper 
{
    private ExpandableMapper expandableMapper; 
    private ExtensionsMapper extensionsMapper; 
    private LinksMapper linksMapper; 

    public ContentMapper(
        ExpandableMapper expandableMapper, 
        ExtensionsMapper extensionsMapper, 
        LinksMapper linksMapper
    ){
        this.expandableMapper = expandableMapper; 
        this.extensionsMapper = extensionsMapper; 
        this.linksMapper = linksMapper; 
    }
    
    public ContentEntity responseToEntity(ContentResponse response)
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

    public ContentDTO entityToDTO(ContentEntity contentEntity)
    {
        try
        {
            ContentDTO contentDTO = new ContentDTO(); 
            contentDTO.id = contentEntity.id; 
            contentDTO.status = contentEntity.status; 
            contentDTO.title = contentEntity.title; 
            contentDTO.type = contentEntity.type; 
            contentDTO._expandable = this.expandableMapper.entityToDTO(contentEntity._expandable);
            contentDTO._links = this.linksMapper.entityToDTO(contentEntity._links);
            contentDTO.extensions = this.extensionsMapper.entityToDTO(contentEntity.extensions);
            return contentDTO;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }
}
