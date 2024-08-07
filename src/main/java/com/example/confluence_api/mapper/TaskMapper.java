package com.example.confluence_api.mapper;

import org.springframework.stereotype.Component;

import com.example.confluence_api.client.model.TaskResponse;
import com.example.confluence_api.dto.TaskDTO;
import com.example.confluence_api.entity.TaskEntity;

@Component
public class TaskMapper 
{
    
    public void convertResponseToEntityWithoutConsideringUsers(TaskEntity entity, TaskResponse response)
    {
        entity.id = response.id;
        entity.localId = response.localId;
        entity.spaceId = response.spaceId;
        entity.pageId = response.pageId;
        entity.blogPostId = response.blogPostId;
        entity.status = response.status;
        entity.createdAt = response.createdAt;
        entity.updatedAt = response.updatedAt;
        entity.dueAt = response.dueAt;
        entity.completedAt = response.completedAt;
    }
    
    public TaskDTO entityToDTO(TaskEntity entity)
    {
        try
        {
            TaskDTO taskDTO = new TaskDTO();
            taskDTO.id = entity.id;
            taskDTO.localId = entity.localId;
            taskDTO.spaceId = entity.spaceId;
            taskDTO.pageId = entity.pageId;
            taskDTO.blogPostId = entity.blogPostId;
            taskDTO.status = entity.status;
            taskDTO.createdBy = entity.createdBy != null ? entity.createdBy.accountId : null;
            taskDTO.assignedTo = entity.assignedTo != null ? entity.assignedTo.accountId : null;
            taskDTO.completedBy = entity.completedBy != null ? entity.completedBy.accountId : null;;
            taskDTO.createdAt = entity.createdAt;
            taskDTO.updatedAt = entity.updatedAt;
            taskDTO.dueAt = entity.dueAt;
            taskDTO.completedAt = entity.completedAt;
            return taskDTO;
        }
        catch(NullPointerException e)
        {
            return null;
        }
    }
}
