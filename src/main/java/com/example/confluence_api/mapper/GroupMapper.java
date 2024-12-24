package com.example.confluence_api.mapper;

import com.example.confluence_api.client.model.ConfluenceGroupResponse;
import com.example.confluence_api.dto.ConfluenceGroupDTO;
import com.example.confluence_api.model.ConfluenceGroupEntity;

import org.springframework.stereotype.Component;

@Component
public class GroupMapper 
{
    public ConfluenceGroupEntity responseToEntity(ConfluenceGroupResponse response)
    {
        try
        {
            ConfluenceGroupEntity entity = new ConfluenceGroupEntity();
            entity.setId(response.getId());
            entity.setName(response.getName());
            entity.setType(response.getType());
            return entity;
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }

    public void convertResponseToEntityWithoutConsideringMembers(ConfluenceGroupEntity entity, ConfluenceGroupResponse response)
    {
        entity.setId(response.getId());
        entity.setName(response.getName());
        entity.setType(response.getType());
    }

    public ConfluenceGroupDTO entityToDTO(ConfluenceGroupEntity entity)
    {
        try
        {
            ConfluenceGroupDTO dto = new ConfluenceGroupDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setType(entity.getType());
            return dto;
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }
}
