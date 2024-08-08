package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.SpaceIconResponse;
import com.example.confluence_api.dto.SpaceIconDTO;
import com.example.confluence_api.entity.SpaceIconEntity;

@Component
public class SpaceIconMapper 
{
    public SpaceIconEntity responseToEntity(SpaceIconResponse response)
    {
        try
        {
            SpaceIconEntity entity = new SpaceIconEntity(); 
            entity.apiDownloadLink = response.apiDownloadLink; 
            entity.path = response.path;
            return entity;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }   
    
    public SpaceIconDTO entityToDTO(SpaceIconEntity entity)
    {
        try
        {
            SpaceIconDTO dto = new SpaceIconDTO(); 
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
