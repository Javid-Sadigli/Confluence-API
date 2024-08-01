package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.api.response.LinksResponse;
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
}
