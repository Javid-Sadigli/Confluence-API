package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.GroupResponse;
import com.example.confluence_api.dto.GroupDTO;
import com.example.confluence_api.entity.GroupEntity;

@Component
public class GroupMapper 
{
    private LinksMapper linksMapper; 

    public GroupMapper(LinksMapper linksMapper)
    {
        this.linksMapper = linksMapper;  
    }

    public GroupEntity responseToEntity(GroupResponse response)
    {
        try
        {
            GroupEntity entity = new GroupEntity();
            entity.id = response.id;
            entity.name = response.name;
            entity.type = response.type;
            entity._links = this.linksMapper.responseToEntity(response._links); 
            return entity;
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }

    public GroupDTO entityToDTO(GroupEntity entity)
    {
        try
        {
            GroupDTO dto = new GroupDTO();
            dto.id = entity.id;
            dto.name = entity.name;
            dto.type = entity.type;
            dto._links = this.linksMapper.entityToDTO(entity._links);
            return dto;
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }
}
