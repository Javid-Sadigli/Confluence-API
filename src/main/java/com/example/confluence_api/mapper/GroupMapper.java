package com.example.confluence_api.mapper;

import org.apache.catalina.Group;
import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.GroupResponse;
import com.example.confluence_api.dto.GroupDTO;
import com.example.confluence_api.entity.GroupEntity;

@Component
public class GroupMapper 
{
    public GroupEntity responseToEntity(GroupResponse response)
    {
        try
        {
            GroupEntity entity = new GroupEntity();
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

    public void convertResponseToEntityWithoutConsideringMembers(GroupEntity entity, GroupResponse response)
    {
        entity.id = response.id;
        entity.name = response.name;
        entity.type = response.type;
    }

    public GroupDTO entityToDTO(GroupEntity entity)
    {
        try
        {
            GroupDTO dto = new GroupDTO();
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
