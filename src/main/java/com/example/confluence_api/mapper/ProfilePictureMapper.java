package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.ConfluenceProfilePictureResponse;
import com.example.confluence_api.dto.ConfluenceProfilePictureDTO;
import com.example.confluence_api.entity.ConfluenceProfilePictureEntity;

@Component
public class ProfilePictureMapper 
{
    public ConfluenceProfilePictureEntity responseToEntity(ConfluenceProfilePictureResponse profilePictureResponse)
    {
        try
        {
            ConfluenceProfilePictureEntity profilePictureEntity = new ConfluenceProfilePictureEntity();
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
    
    public ConfluenceProfilePictureDTO entityToDTO(ConfluenceProfilePictureEntity entity)
    {
        try
        {
            ConfluenceProfilePictureDTO profilePictureDTO = new ConfluenceProfilePictureDTO();
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

