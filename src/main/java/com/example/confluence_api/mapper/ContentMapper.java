package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.ConfluenceContentResponse;
import com.example.confluence_api.dto.ConfluenceContentDTO;
import com.example.confluence_api.entity.ConfluenceContentEntity;

@Component
public class ContentMapper 
{
    public ConfluenceContentEntity responseToEntity(ConfluenceContentResponse response)
    {
        try
        {
            ConfluenceContentEntity contentEntity = new ConfluenceContentEntity(); 
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

    public void convertResponseToEntityWithoutConsideringTasks(ConfluenceContentEntity entity, ConfluenceContentResponse response)
    {
        entity.id = response.id;
        entity.status = response.status;
        entity.title = response.title;
        entity.type = response.type;
    }

    public ConfluenceContentDTO entityToDTO(ConfluenceContentEntity contentEntity)
    {
        try
        {
            ConfluenceContentDTO contentDTO = new ConfluenceContentDTO(); 
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
