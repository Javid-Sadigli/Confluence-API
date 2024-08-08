package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.ConfluenceUserResponse;
import com.example.confluence_api.dto.ConfluenceUserDTO;
import com.example.confluence_api.entity.ConfluenceUserEntity;

@Component
public class UserMapper 
{
    private final ProfilePictureMapper profilePictureMapper; 

    public UserMapper(ProfilePictureMapper profilePictureMapper)
    {
        this.profilePictureMapper = profilePictureMapper;
    }

    public ConfluenceUserEntity responseToEntity(ConfluenceUserResponse response)
    {
        try
        {
            ConfluenceUserEntity entity = new ConfluenceUserEntity();
            entity.accountId = response.accountId;
            entity.accountType = response.accountType;
            entity.email = response.email;
            entity.displayName = response.displayName;
            entity.isExternalCollaborator = response.isExternalCollaborator;
            entity.isGuest = response.isGuest; 
            entity.publicName = response.publicName;
            entity.type = response.type;
            entity.profilePicture = this.profilePictureMapper.responseToEntity(response.profilePicture); 
            return entity;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }

    public void convertResponseToEntityWithoutConsideringTasks(ConfluenceUserEntity entity, ConfluenceUserResponse response)
    {
        entity.accountId = response.accountId;
        entity.accountType = response.accountType;
        entity.email = response.email;
        entity.displayName = response.displayName;
        entity.isExternalCollaborator = response.isExternalCollaborator;
        entity.isGuest = response.isGuest;
        entity.publicName = response.publicName;
        entity.type = response.type;
        entity.profilePicture = this.profilePictureMapper.responseToEntity(response.profilePicture);
    }

    public ConfluenceUserDTO entityToDTO(ConfluenceUserEntity entity) 
    {
        try
        {
            ConfluenceUserDTO dto = new ConfluenceUserDTO();
            dto.accountId = entity.accountId;
            dto.accountType = entity.accountType;
            dto.email = entity.email;
            dto.displayName = entity.displayName;
            dto.isExternalCollaborator = entity.isExternalCollaborator;
            dto.isGuest = entity.isGuest;
            dto.publicName = entity.publicName;
            dto.type = entity.type;
            dto.profilePicture = this.profilePictureMapper.entityToDTO(entity.profilePicture);
            return dto;
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }
}
