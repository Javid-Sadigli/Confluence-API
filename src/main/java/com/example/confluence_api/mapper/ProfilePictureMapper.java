package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.ProfilePictureResponse;
import com.example.confluence_api.dto.ProfilePictureDTO;
import com.example.confluence_api.entity.ProfilePictureEntity;

@Component
public class ProfilePictureMapper 
{
    public ProfilePictureEntity responseToEntity(ProfilePictureResponse profilePictureResponse)
    {
        try
        {
            ProfilePictureEntity profilePictureEntity = new ProfilePictureEntity();
            profilePictureEntity.height = profilePictureResponse.height; 
            profilePictureEntity.width = profilePictureResponse.width;
            profilePictureEntity.isDefault = profilePictureResponse.isDefault; 
            profilePictureEntity.path = profilePictureResponse.path;
            return profilePictureEntity;
        }
        catch(NullPointerException e)
        {
            return null; 
        }
    }
    
    public ProfilePictureDTO entityToDTO(ProfilePictureEntity entity)
    {
        try
        {
            ProfilePictureDTO profilePictureDTO = new ProfilePictureDTO();
            profilePictureDTO.height = entity.height;
            profilePictureDTO.width = entity.width;
            profilePictureDTO.isDefault = entity.isDefault;
            profilePictureDTO.path = entity.path;
            return profilePictureDTO;
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }
}

