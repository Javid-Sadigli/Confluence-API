package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.ConfluenceSpaceResponse;
import com.example.confluence_api.dto.ConfluenceSpaceDTO;
import com.example.confluence_api.entity.ConfluenceSpaceEntity;

@Component
public class SpaceMapper 
{
    private final SpaceIconMapper spaceIconMapper; 

    public SpaceMapper(SpaceIconMapper spaceIconMapper) 
    {
        this.spaceIconMapper = spaceIconMapper;
    }

    public void convertResponseToEntityWithoutConsideringRelations(ConfluenceSpaceEntity entity, ConfluenceSpaceResponse response)
    {
        entity.id = response.id; 
        entity.key = response.key;
        entity.name = response.name;
        entity.type = response.type;
        entity.status = response.status;
        entity.createdAt = response.createdAt;
    }

    public ConfluenceSpaceDTO entityToDTO(ConfluenceSpaceEntity entity)
    {
        try
        {
            ConfluenceSpaceDTO dto = new ConfluenceSpaceDTO();
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
