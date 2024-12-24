package com.example.confluence_api.mapper;


import com.example.confluence_api.client.model.ConfluenceSpaceIconResponse;
import com.example.confluence_api.dto.ConfluenceSpaceIconDTO;
import com.example.confluence_api.model.ConfluenceSpaceIconEntity;

import org.springframework.stereotype.Component;

@Component
public class SpaceIconMapper 
{
    public ConfluenceSpaceIconEntity responseToEntity(ConfluenceSpaceIconResponse response)
    {
        try
        {
            ConfluenceSpaceIconEntity entity = new ConfluenceSpaceIconEntity(); 
            entity.setApiDownloadLink(response.getApiDownloadLink());
            entity.setPath(response.getPath());
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
            dto.setApiDownloadLink(entity.getApiDownloadLink());
            dto.setApiDownloadLink(entity.getApiDownloadLink());
            return dto;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }
}
