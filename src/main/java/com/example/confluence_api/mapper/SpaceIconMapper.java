package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.ConfluenceSpaceIconResponse;
import com.example.confluence_api.dto.ConfluenceSpaceIconDTO;
import com.example.confluence_api.entity.ConfluenceSpaceIconEntity;

@Component
public class SpaceIconMapper 
{
    public ConfluenceSpaceIconEntity responseToEntity(ConfluenceSpaceIconResponse response)
    {
        try
        {
            ConfluenceSpaceIconEntity entity = new ConfluenceSpaceIconEntity(); 
            entity.apiDownloadLink = response.apiDownloadLink; 
            entity.path = response.path;
            return entity;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }   
    
    public ConfluenceSpaceIconDTO entityToDTO(ConfluenceSpaceIconEntity entity)
    {
        try
        {
            ConfluenceSpaceIconDTO dto = new ConfluenceSpaceIconDTO(); 
            dto.apiDownloadLink = entity.apiDownloadLink; 
            dto.path = entity.path;
            return dto;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }
}
