package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.ConfluenceContentVersionResponse;
import com.example.confluence_api.dto.ConfluenceContentVersionDTO;
import com.example.confluence_api.model.ConfluenceContentVersionEntity;
import com.example.confluence_api.model.ConfluenceUserEntity;

@Component
public class ContentVersionMapper 
{
    private final UserMapper userMapper;

    public ContentVersionMapper(UserMapper userMapper)
    {
        this.userMapper = userMapper;
    }

    public ConfluenceContentVersionEntity responseToEntity(ConfluenceContentVersionResponse response)
    {
        try
        {
            ConfluenceContentVersionEntity entity = new ConfluenceContentVersionEntity(); 
            entity.setBy(new ConfluenceUserEntity());
            this.userMapper.convertResponseToEntityWithoutConsideringTasks(entity.getBy(), response.getBy());
            entity.setWhen(response.getWhen());
            entity.setFriendlyWhen(response.getFriendlyWhen());
            entity.setMessage(response.getMessage());
            entity.setNumber(response.getNumber());
            entity.setMinorEdit(response.isMinorEdit());
            entity.setNcsStepVersion(response.getNcsStepVersion());
            entity.setNcsStepVersionSource(response.getNcsStepVersionSource());
            entity.setConfRev(response.getConfRev());
            entity.setContentTypeModified(response.isContentTypeModified());
            return entity;
        }
        catch(NullPointerException e)
        {
            return null;  
        }
    }

    public void convertResponseToEntityWithoutConsideringRelations(
        ConfluenceContentVersionEntity entity,
        ConfluenceContentVersionResponse response
    ){
        entity.setWhen(response.getWhen());
        entity.setFriendlyWhen(response.getFriendlyWhen());
        entity.setMessage(response.getMessage());
        entity.setNumber(response.getNumber());
        entity.setMinorEdit(response.isMinorEdit());
        entity.setNcsStepVersion(response.getNcsStepVersion());
        entity.setNcsStepVersionSource(response.getNcsStepVersionSource());
        entity.setConfRev(response.getConfRev());
        entity.setContentTypeModified(response.isContentTypeModified());
    }

    public ConfluenceContentVersionDTO entityToDTO(ConfluenceContentVersionEntity entity)
    {
        try
        {
            ConfluenceContentVersionDTO dto = new ConfluenceContentVersionDTO();
            dto.setBy(this.userMapper.entityToDTO(entity.getBy()));
            dto.setWhen(entity.getWhen());
            dto.setFriendlyWhen(entity.getFriendlyWhen());
            dto.setMessage(entity.getMessage());
            dto.setNumber(entity.getNumber());
            dto.setMinorEdit(entity.isMinorEdit());
            dto.setNcsStepVersion(entity.getNcsStepVersion());
            dto.setNcsStepVersionSource(entity.getNcsStepVersionSource());
            dto.setConfRev(entity.getConfRev());
            dto.setContentTypeModified(entity.isContentTypeModified());
            dto.setAddedLines(entity.getAddedLines());
            dto.setTotalLines(entity.getTotalLines());
            return dto;
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }
}
