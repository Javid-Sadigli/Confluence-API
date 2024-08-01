package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.api.response.ExpandableResponse;
import com.example.confluence_api.entity.ExpandableEntity;

@Component
public class ExpandableMapper 
{
    public ExpandableEntity responseToEntity(ExpandableResponse response)
    {
        try
        {
            ExpandableEntity entity = new ExpandableEntity(); 
            entity.container = response.container; 
            entity.metadata = response.metadata; 
            entity.restrictions = response.restrictions; 
            entity.history = response.history; 
            entity.body = response.body; 
            entity.version = response.version; 
            entity.descendants = response.descendants; 
            entity.space = response.space; 
            entity.childTypes = response.childTypes; 
            entity.schedulePublishInfo = response.schedulePublishInfo; 
            entity.operations = response.operations; 
            entity.schedulePublishDate = response.schedulePublishDate; 
            entity.children = response.children; 
            entity.ancestors = response.ancestors; 
            return entity;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }
}
