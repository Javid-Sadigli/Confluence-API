package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.ConfluenceGroupResponse;
import com.example.confluence_api.dto.ConfluenceGroupDTO;
import com.example.confluence_api.entity.ConfluenceGroupEntity;

@Component
public class GroupMapper 
{
    public ConfluenceGroupEntity responseToEntity(ConfluenceGroupResponse response)
    {
        try
        {
            ConfluenceGroupEntity entity = new ConfluenceGroupEntity();
            entity.id = response.id;
            entity.name = response.name;
            entity.type = response.type;
            return entity;
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }

    public void convertResponseToEntityWithoutConsideringMembers(ConfluenceGroupEntity entity, ConfluenceGroupResponse response)
    {
        entity.id = response.id;
        entity.name = response.name;
        entity.type = response.type;
    }

    public ConfluenceGroupDTO entityToDTO(ConfluenceGroupEntity entity)
    {
        try
        {
            ConfluenceGroupDTO dto = new ConfluenceGroupDTO();
            dto.id = entity.id;
            dto.name = entity.name;
            dto.type = entity.type;
            return dto;
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }
}
