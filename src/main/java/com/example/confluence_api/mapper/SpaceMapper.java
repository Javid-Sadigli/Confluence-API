package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.SpaceResponse;
import com.example.confluence_api.dto.SpaceDTO;
import com.example.confluence_api.entity.SpaceEntity;

@Component
public class SpaceMapper 
{
    private final SpaceIconMapper spaceIconMapper; 

    public SpaceMapper(SpaceIconMapper spaceIconMapper) 
    {
        this.spaceIconMapper = spaceIconMapper;
    }

    public void convertResponseToEntityWithoutConsideringRelations(SpaceEntity entity, SpaceResponse response)
    {
        entity.id = response.id; 
        entity.key = response.key;
        entity.name = response.name;
        entity.type = response.type;
        entity.status = response.status;
        entity.createdAt = response.createdAt;
    }

    public SpaceDTO entityToDTO(SpaceEntity entity)
    {
        try
        {
            SpaceDTO dto = new SpaceDTO();
            dto.id = entity.id;
            dto.key = entity.key;
            dto.name = entity.name;
            dto.type = entity.type;
            dto.status = entity.status;
            dto.createdAt = entity.createdAt;
            dto.authorId = entity.author != null ? entity.author.accountId : null; 
            dto.homepageId = entity.homepage != null ? entity.homepage.id : null;
            dto.icon = spaceIconMapper.entityToDTO(entity.icon);
            return dto;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }
}
