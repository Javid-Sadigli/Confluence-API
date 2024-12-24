package com.example.confluence_api.mapper;


import com.example.confluence_api.client.model.ConfluenceTaskResponse;
import com.example.confluence_api.dto.ConfluenceTaskDTO;
import com.example.confluence_api.model.ConfluenceTaskEntity;

import org.springframework.stereotype.Component;

@Component
public class TaskMapper 
{
    
    public void convertResponseToEntityWithoutConsideringRelations(ConfluenceTaskEntity entity, ConfluenceTaskResponse response)
    {
        entity.setId(response.getId());
        entity.setLocalId(response.getLocalId());
        entity.setBlogPostId(response.getBlogPostId());
        entity.setStatus(response.getStatus());
        entity.setCreatedAt(response.createdAt());
        entity.setUpdatedAt(response.updatedAt());
        entity.setDueAt(response.dueAt());
        entity.setCompletedAt(response.completedAt());
    }
    
    public ConfluenceTaskDTO entityToDTO(ConfluenceTaskEntity entity)
    {
        try
        {
            ConfluenceTaskDTO taskDTO = new ConfluenceTaskDTO();
            taskDTO.setId(entity.getId());
            taskDTO.setLocalId(entity.getLocalId());
            taskDTO.setSpaceId(entity.getSpace() != null ? entity.getSpace().getId() : null);
            taskDTO.setPageId(entity.getPage() != null ? entity.getPage().getId() : null);
            taskDTO.setBlogPostId(entity.getBlogPostId());
            taskDTO.setStatus(entity.getStatus());
            taskDTO.setCreatedBy(entity.createdBy() != null ? entity.createdBy().getAccountId() : null);
            taskDTO.setAssignedTo(entity.assignedTo() != null ? entity.assignedTo().getAccountId() : null);
            taskDTO.setCompletedBy(entity.completedBy() != null ? entity.completedBy().getAccountId() : null);
            taskDTO.setCreatedAt(entity.createdAt());
            taskDTO.setUpdatedAt(entity.updatedAt());
            taskDTO.setDueAt(entity.dueAt());
            taskDTO.setCompletedAt(entity.completedAt());
            return taskDTO;
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }
}
