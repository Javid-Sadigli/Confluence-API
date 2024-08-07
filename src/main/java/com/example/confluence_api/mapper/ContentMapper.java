package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.ContentResponse;
import com.example.confluence_api.dto.ContentDTO;
import com.example.confluence_api.entity.ContentEntity;

@Component
public class ContentMapper 
{
    public ContentEntity responseToEntity(ContentResponse response)
    {
        try
        {
            ContentEntity contentEntity = new ContentEntity(); 
            contentEntity.id = response.id; 
            contentEntity.status = response.status; 
            contentEntity.title = response.title; 
            contentEntity.type = response.type; 
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
            return contentDTO;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }
}
