package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.api.response.LinksResponse;
import com.example.confluence_api.dto.LinksDTO;
import com.example.confluence_api.entity.LinksEntity;

@Component
public class LinksMapper 
{
    public LinksEntity responseToEntity(LinksResponse response)
    {
        try
        {
            LinksEntity entity = new LinksEntity(); 
            entity.self = response.self; 
            entity.tinyui = response.tinyui; 
            entity.editui = response.editui; 
            entity.webui = response.webui; 
            entity.base = response.base; 
            entity.context = response.context; 
            return entity;         
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }

    public LinksDTO entityToDTO(LinksEntity entity)
    {
        try
        {
            LinksDTO dto = new LinksDTO(); 
            dto.self = entity.self; 
            dto.tinyui = entity.tinyui; 
            dto.editui = entity.editui; 
            dto.webui = entity.webui; 
            dto.base = entity.base; 
            dto.context = entity.context; 
            return dto;         
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }
}
