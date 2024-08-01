package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.api.response.ExtensionsResponse;
import com.example.confluence_api.entity.ExtensionsEntity;

@Component
public class ExtensionsMapper 
{
    public ExtensionsEntity responseToEntity(ExtensionsResponse response)
    {
        try
        {
            ExtensionsEntity entity = new ExtensionsEntity(); 
            entity.position = response.position; 
            return entity;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }
}
