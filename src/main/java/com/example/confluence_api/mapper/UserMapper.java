package com.example.confluence_api.mapper.confluence;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.UserResponse;
import com.example.confluence_api.dto.UserDTO;
import com.example.confluence_api.entity.UserEntity;

@Component
public class UserMapper 
{
    private final ProfilePictureMapper profilePictureMapper; 

    public UserMapper(ProfilePictureMapper profilePictureMapper)
    {
        this.profilePictureMapper = profilePictureMapper;
    }

    public UserEntity responseToEntity(UserResponse response)
    {
        try
        {
            UserEntity entity = new UserEntity();
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

    public void convertResponseToEntityWithoutConsideringTasks(UserEntity entity, UserResponse response)
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

    public UserDTO entityToDTO(UserEntity entity) 
    {
        try
        {
            UserDTO dto = new UserDTO();
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
