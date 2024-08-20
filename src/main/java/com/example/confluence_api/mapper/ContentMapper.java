package com.example.confluence_api.mapper;

import com.example.confluence_api.client.model.ConfluenceContentResponse;
import com.example.confluence_api.dto.ConfluenceContentDTO;
import com.example.confluence_api.model.ConfluenceContentEntity;

import org.springframework.stereotype.Component;

@Component
public class ContentMapper 
{
    public ConfluenceContentEntity responseToEntity(ConfluenceContentResponse response)
    {
        try
        {
            ConfluenceContentEntity contentEntity = new ConfluenceContentEntity(); 
            contentEntity.setId(response.getId());
            contentEntity.setStatus(response.getStatus());
            contentEntity.setTitle(response.getTitle());
            contentEntity.setType(response.getType());
            return contentEntity; 
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }

    public void convertResponseToEntityWithoutConsideringRelations(ConfluenceContentEntity entity, ConfluenceContentResponse response)
    {
        entity.setId(response.getId());
        entity.setStatus(response.getStatus());
        entity.setTitle(response.getTitle());
        entity.setType(response.getType());
    }

    public ConfluenceContentDTO entityToDTO(ConfluenceContentEntity contentEntity)
    {
        try
        {
            ConfluenceContentDTO contentDTO = new ConfluenceContentDTO(); 
            contentDTO.setId(contentEntity.getId());
            contentDTO.setStatus(contentEntity.getStatus());
            contentDTO.setTitle(contentEntity.getTitle());
            contentDTO.setType(contentEntity.getType());
            return contentDTO;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }
}
