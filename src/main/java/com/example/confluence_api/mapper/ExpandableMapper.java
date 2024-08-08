package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.ExpandableResponse;
import com.example.confluence_api.dto.ExpandableDTO;
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

    public ExpandableDTO entityToDTO(ExpandableEntity expandableEntity)
    {
        try
        {
            ExpandableDTO expandableDTO = new ExpandableDTO();
            expandableDTO.container = expandableEntity.container; 
            expandableDTO.metadata = expandableEntity.metadata; 
            expandableDTO.restrictions = expandableEntity.restrictions; 
            expandableDTO.history = expandableEntity.history; 
            expandableDTO.body = expandableEntity.body; 
            expandableDTO.version = expandableEntity.version;
            expandableDTO.descendants = expandableEntity.descendants;
            expandableDTO.space = expandableEntity.space;
            expandableDTO.childTypes = expandableEntity.childTypes;
            expandableDTO.schedulePublishInfo = expandableEntity.schedulePublishInfo;
            expandableDTO.operations = expandableEntity.operations;
            expandableDTO.schedulePublishDate = expandableEntity.schedulePublishDate;
            expandableDTO.children = expandableEntity.children;
            expandableDTO.ancestors = expandableEntity.ancestors;
            return expandableDTO;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }
}
