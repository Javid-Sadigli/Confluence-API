package com.example.confluence_api.mapper;


import com.example.confluence_api.client.model.ConfluenceSpaceResponse;
import com.example.confluence_api.dto.ConfluenceSpaceDTO;
import com.example.confluence_api.model.ConfluenceSpaceEntity;

import org.springframework.stereotype.Component;

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
        entity.setId(response.getId());
        entity.setKey(response.getKey());
        entity.setName(response.getName());
        entity.setType(response.getType());
        entity.setStatus(response.getStatus());
        entity.setCreatedAt(response.createdAt());
    }

    public ConfluenceSpaceDTO entityToDTO(ConfluenceSpaceEntity entity)
    {
        try
        {
            ConfluenceSpaceDTO dto = new ConfluenceSpaceDTO();
            dto.setId(entity.getId());
            dto.setKey(entity.getKey());
            dto.setName(entity.getName());
            dto.setType(entity.getType());
            dto.setStatus(entity.getStatus());
            dto.setCreatedAt(entity.createdAt());
            dto.setAuthorId(entity.getAuthor() != null ? entity.getAuthor().getAccountId() : null);
            dto.setHomepageId(entity.getHomepage() != null ? entity.getHomepage().getId() : null);
            dto.setIcon(spaceIconMapper.entityToDTO(entity.getIcon()));

            return dto;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }
}
