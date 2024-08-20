package com.example.confluence_api.mapper;

import com.example.confluence_api.client.model.ConfluenceUserResponse;
import com.example.confluence_api.dto.ConfluenceUserDTO;
import com.example.confluence_api.model.ConfluenceUserEntity;

import org.springframework.stereotype.Component;

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
            entity.setAccountId(response.getAccountId());
            entity.setAccountType(response.getAccountType());
            entity.setEmail(response.getEmail());
            entity.setDisplayName(response.getDisplayName());
            entity.setIsExternalCollaborator(response.isExternalCollaborator());
            entity.setIsGuest(response.isGuest());
            entity.setPublicName(response.getPublicName());
            entity.setType(response.getType());
            entity.setProfilePicture(this.profilePictureMapper.responseToEntity(response.getProfilePicture()));
            return entity;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }

    public void convertResponseToEntityWithoutConsideringTasks(ConfluenceUserEntity entity, ConfluenceUserResponse response)
    {
        entity.setAccountId(response.getAccountId());
        entity.setAccountType(response.getAccountType());
        entity.setEmail(response.getEmail());
        entity.setDisplayName(response.getDisplayName());
        entity.setIsExternalCollaborator(response.isExternalCollaborator());
        entity.setIsGuest(response.isGuest());
        entity.setPublicName(response.getPublicName());
        entity.setType(response.getType());
        entity.setProfilePicture(this.profilePictureMapper.responseToEntity(response.getProfilePicture()));
    }

    public ConfluenceUserDTO entityToDTO(ConfluenceUserEntity entity) 
    {
        try
        {
            ConfluenceUserDTO dto = new ConfluenceUserDTO();
            dto.setAccountId(entity.getAccountId());
            dto.setAccountType(entity.getAccountType());
            dto.setEmail(entity.getEmail());
            dto.setDisplayName(entity.getDisplayName());
            dto.setIsExternalCollaborator(entity.isExternalCollaborator());
            dto.setIsGuest(entity.isGuest());
            dto.setPublicName(entity.getPublicName());
            dto.setType(entity.getType());
            dto.setProfilePicture(this.profilePictureMapper.entityToDTO(entity.getProfilePicture()));
            return dto;
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }
}
