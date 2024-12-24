package com.example.confluence_api.mapper;

import com.example.confluence_api.client.model.ConfluenceProfilePictureResponse;
import com.example.confluence_api.dto.ConfluenceProfilePictureDTO;
import com.example.confluence_api.model.ConfluenceProfilePictureEntity;

import org.springframework.stereotype.Component;

@Component
public class ProfilePictureMapper 
{
    public ConfluenceProfilePictureEntity responseToEntity(ConfluenceProfilePictureResponse profilePictureResponse)
    {
        try
        {
            ConfluenceProfilePictureEntity profilePictureEntity = new ConfluenceProfilePictureEntity();
            profilePictureEntity.setHeight(profilePictureResponse.getHeight());
            profilePictureEntity.setWidth(profilePictureResponse.getWidth());
            profilePictureEntity.setIsDefault(profilePictureResponse.isDefault());
            profilePictureEntity.setPath(profilePictureResponse.getPath());
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
            profilePictureDTO.setHeight(entity.getHeight());
            profilePictureDTO.setWidth(entity.getWidth());
            profilePictureDTO.setIsDefault(entity.isDefault());
            profilePictureDTO.setPath(entity.getPath());
            return profilePictureDTO;
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }
}

