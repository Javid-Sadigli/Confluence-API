package com.example.confluence_api.service;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.confluence_api.client.ConfluenceClient;
import com.example.confluence_api.client.model.ConfluenceRootResponse;
import com.example.confluence_api.client.model.ContentResponse;
import com.example.confluence_api.client.model.GroupResponse;
import com.example.confluence_api.client.model.TaskResponse;
import com.example.confluence_api.client.model.UserResponse;
import com.example.confluence_api.dto.ConfluenceRootDTO;
import com.example.confluence_api.dto.ContentDTO;
import com.example.confluence_api.dto.GroupDTO;
import com.example.confluence_api.dto.TaskDTO;
import com.example.confluence_api.dto.UserDTO;
import com.example.confluence_api.entity.ContentEntity;
import com.example.confluence_api.entity.GroupEntity;
import com.example.confluence_api.entity.TaskEntity;
import com.example.confluence_api.entity.UserEntity;
import com.example.confluence_api.mapper.ContentMapper;
import com.example.confluence_api.mapper.GroupMapper;
import com.example.confluence_api.mapper.TaskMapper;
import com.example.confluence_api.mapper.UserMapper;
import com.example.confluence_api.repository.ConfluenceContentRepository;
import com.example.confluence_api.repository.ConfluenceGroupRepository;
import com.example.confluence_api.repository.ConfluenceTaskRepository;
import com.example.confluence_api.repository.ConfluenceUserRepository;

@Service
public class ConfluenceService 
{
    private final ContentMapper contentMapper; 
    private final GroupMapper groupMapper; 
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;
    private final ConfluenceClient confluenceClient; 
    private final ConfluenceContentRepository confluenceContentRepository; 
    private final ConfluenceGroupRepository confluenceGroupRepository; 
    private final ConfluenceUserRepository confluenceUserRepository; 
    private final ConfluenceTaskRepository confluenceTaskRepository; 

    public ConfluenceService(
        ContentMapper contentMapper, 
        GroupMapper groupMapper, 
        UserMapper userMapper,
        TaskMapper taskMapper,  
        ConfluenceClient confluenceClient, 
        ConfluenceContentRepository confluenceContentRepository, 
        ConfluenceGroupRepository confluenceGroupRepository, 
        ConfluenceUserRepository confluenceUserRepository, 
        ConfluenceTaskRepository confluenceTaskRepository
    ){
        this.confluenceClient = confluenceClient; 
        this.contentMapper = contentMapper; 
        this.groupMapper = groupMapper; 
        this.userMapper = userMapper;
        this.taskMapper = taskMapper;  
        this.confluenceContentRepository = confluenceContentRepository; 
        this.confluenceGroupRepository = confluenceGroupRepository;
        this.confluenceUserRepository = confluenceUserRepository;
        this.confluenceTaskRepository = confluenceTaskRepository;
    }


    /* -------------------- CONTENT METHODS  -------------------- */

    public ConfluenceRootDTO<ContentDTO> saveContents()
    {
        ConfluenceRootDTO<ContentDTO> dto = new ConfluenceRootDTO<ContentDTO>();
        ConfluenceRootResponse<ContentResponse> response = this.confluenceClient.fetchAllContents();
        
        ArrayList<ContentEntity> contents = new ArrayList<ContentEntity>();

        dto.size = response.size; 
        dto.results = new ArrayList<ContentDTO>();

        response.results.forEach((result) -> {
            ContentEntity contentEntity = this.contentMapper.responseToEntity(result); 
            contents.add(contentEntity);
            ContentDTO contentDTO = this.contentMapper.entityToDTO(contentEntity);
            dto.results.add(contentDTO); 
        });

        this.confluenceContentRepository.saveAll(contents);
        
        return dto; 
    }

    public ConfluenceRootDTO<ContentDTO> getAllContents(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size); 
        Page<ContentEntity> contentPage = this.confluenceContentRepository.findAll(pageable);

        ConfluenceRootDTO<ContentDTO> dto = new ConfluenceRootDTO<ContentDTO>();
        ArrayList<ContentEntity> contentEntities = new ArrayList<ContentEntity>(contentPage.getContent()); 
        
        dto.size = contentEntities.size();
        dto.pageNumber = pageNumber; 
        dto.results = new ArrayList<ContentDTO>();
        
        contentEntities.forEach((contentEntity) -> {
            ContentDTO contentDTO = this.contentMapper.entityToDTO(contentEntity);
            dto.results.add(contentDTO); 
        });

        return dto;
    }

    public ContentDTO getContentById(String id)
    {
        ContentEntity contentEntity = this.confluenceContentRepository.findById(id).orElse(null);
        return this.contentMapper.entityToDTO(contentEntity); 
    }


    /* -------------------- GROUP METHODS  -------------------- */

    public ConfluenceRootDTO<GroupDTO> saveGroups()
    {
        ConfluenceRootDTO<GroupDTO> dto = new ConfluenceRootDTO<GroupDTO>();
        ConfluenceRootResponse<GroupResponse> response = this.confluenceClient.fetchAllGroups();
        
        ArrayList<GroupEntity> groups = new ArrayList<GroupEntity>();

        dto.size = response.size; 
        dto.results = new ArrayList<GroupDTO>();

        response.results.forEach((result) -> {
            GroupEntity groupEntity = this.confluenceGroupRepository.findById(result.id).orElse(new GroupEntity()); 
            this.groupMapper.convertResponseToEntityWithoutConsideringMembers(groupEntity, result);
            groups.add(groupEntity);
            GroupDTO groupDTO = this.groupMapper.entityToDTO(groupEntity);
            dto.results.add(groupDTO); 
        });

        this.confluenceGroupRepository.saveAll(groups);

        return dto;
    }

    public ConfluenceRootDTO<GroupDTO> getAllGroups(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size); 
        Page<GroupEntity> groupPage = this.confluenceGroupRepository.findAll(pageable);

        ConfluenceRootDTO<GroupDTO> dto = new ConfluenceRootDTO<GroupDTO>();
        ArrayList<GroupEntity> groupEntities = new ArrayList<GroupEntity>(groupPage.getContent()); 
        
        dto.size = groupEntities.size();
        dto.pageNumber = pageNumber;
        dto.results = new ArrayList<GroupDTO>();
        
        groupEntities.forEach((groupEntity) -> {
            GroupDTO groupDTO = this.groupMapper.entityToDTO(groupEntity);
            dto.results.add(groupDTO); 
        });

        return dto; 
    }

    public GroupDTO getGroupById(String id)
    {
        GroupEntity groupEntity = this.confluenceGroupRepository.findById(id).orElse(null);
        return this.groupMapper.entityToDTO(groupEntity);
    }

    public ConfluenceRootDTO<UserDTO> saveGroupMembers(String groupId)
    {
        ConfluenceRootResponse<UserResponse> response = this.confluenceClient.fetchUsersForAGroup(groupId); 
        ConfluenceRootDTO<UserDTO> dto = new ConfluenceRootDTO<UserDTO>();
        GroupEntity groupEntity = this.confluenceGroupRepository.findById(groupId).orElse(null);

        dto.size = response.size;
        dto.results = new ArrayList<UserDTO>();

        response.results.forEach((member) -> {
            UserEntity userEntity = this.confluenceUserRepository.findById(member.accountId).orElse(new UserEntity());
            this.userMapper.convertResponseToEntityWithoutConsideringTasks(userEntity, member); 
            this.confluenceUserRepository.save(userEntity);
            UserDTO userDTO = this.userMapper.entityToDTO(userEntity);
            dto.results.add(userDTO);
            groupEntity.members.add(userEntity); 
        });

        this.confluenceGroupRepository.save(groupEntity); 
        return dto; 
    }

    public ConfluenceRootDTO<UserDTO> getGroupMembers(String groupId)
    {
        ConfluenceRootDTO<UserDTO> dto = new ConfluenceRootDTO<UserDTO>();
        GroupEntity groupEntity = this.confluenceGroupRepository.findById(groupId).orElse(null);

        try
        {
            dto.size = groupEntity.members.size();
            dto.results = new ArrayList<UserDTO>();
            groupEntity.members.forEach((member) -> {
                UserDTO userDTO = this.userMapper.entityToDTO(member);
                dto.results.add(userDTO);
            });
        }
        catch(NullPointerException e)
        {
            dto.message = "Cannot find group that matches groupId: " + groupId; 
        }

        return dto; 
    }

    /* -------------------- USER METHODS  -------------------- */

    public ConfluenceRootDTO<UserDTO> saveUsers()
    {
        ConfluenceRootDTO<UserDTO> dto = new ConfluenceRootDTO<UserDTO>();
        ConfluenceRootResponse<GroupResponse> groups = this.confluenceClient.fetchAllGroups();

        HashSet<UserEntity> users = new HashSet<UserEntity>();
        
        HashSet<UserDTO> setOfUsers = new HashSet<UserDTO>();

        groups.results.stream().forEach((group) -> {
            ConfluenceRootResponse<UserResponse> groupMembers = this.confluenceClient.fetchUsersForAGroup(group.id);
            groupMembers.results.stream().forEach((member) -> {
                UserEntity userEntity = this.confluenceUserRepository.findById(member.accountId).orElse(new UserEntity());
                this.userMapper.convertResponseToEntityWithoutConsideringTasks(userEntity, member);
                users.add(userEntity); 
                UserDTO userDTO = this.userMapper.entityToDTO(userEntity);
                setOfUsers.add(userDTO);
            });
        });

        dto.size = users.size();
        dto.results = new ArrayList<UserDTO>(setOfUsers);

        this.confluenceUserRepository.saveAll(users);

        return dto; 
    }

    public ConfluenceRootDTO<UserDTO> getAllUsers(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<UserEntity> userPage = this.confluenceUserRepository.findAll(pageable);

        ConfluenceRootDTO<UserDTO> dto = new ConfluenceRootDTO<UserDTO>();
        ArrayList<UserEntity> userEntities = new ArrayList<UserEntity>(userPage.getContent());
        
        dto.size = userEntities.size();
        dto.pageNumber = pageNumber;
        dto.results = new ArrayList<UserDTO>();
        
        userEntities.forEach((userEntity) -> {
            UserDTO userDTO = this.userMapper.entityToDTO(userEntity);
            dto.results.add(userDTO);
        });

        return dto;
    }

    public UserDTO getUserById(String userId)
    {
        UserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);
        return this.userMapper.entityToDTO(userEntity);
    }

    public ConfluenceRootDTO<TaskDTO> getUserCompletedTasks(String userId)
    {
        ConfluenceRootDTO<TaskDTO> dto = new ConfluenceRootDTO<TaskDTO>();
        UserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);

        try
        {
            dto.size = userEntity.completedTasks.size();
            dto.results = new ArrayList<TaskDTO>();
            userEntity.completedTasks.forEach((task) -> {
                TaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                dto.results.add(taskDTO);
            });
        }
        catch(NullPointerException e)
        {
            dto.message = "Cannot find user that matches userId: " + userId;
        }

        return dto;
    }

    public ConfluenceRootDTO<TaskDTO> getUserAssignedTasks(String userId)
    {
        ConfluenceRootDTO<TaskDTO> dto = new ConfluenceRootDTO<TaskDTO>();
        UserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);

        try
        {
            dto.size = userEntity.assignedTasks.size();
            dto.results = new ArrayList<TaskDTO>();
            userEntity.assignedTasks.forEach((task) -> {
                TaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                dto.results.add(taskDTO);
            });
        }
        catch(NullPointerException e)
        {
            dto.message = "Cannot find user that matches userId: " + userId;
        }

        return dto;
    }

    public ConfluenceRootDTO<TaskDTO> getUserCreatedTasks(String userId)
    {
        ConfluenceRootDTO<TaskDTO> dto = new ConfluenceRootDTO<TaskDTO>();
        UserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);

        try
        {
            dto.size = userEntity.createdTasks.size();
            dto.results = new ArrayList<TaskDTO>();
            userEntity.createdTasks.forEach((task) -> {
                TaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                dto.results.add(taskDTO);
            });
        }
        catch(NullPointerException e)
        {
            dto.message = "Cannot find user that matches userId: " + userId;
        }

        return dto;
    }

    /* -------------------- TASK METHODS  -------------------- */

    public ConfluenceRootDTO<TaskDTO> saveTasks()
    {
        ConfluenceRootDTO<TaskDTO> dto = new ConfluenceRootDTO<TaskDTO>();
        ConfluenceRootResponse<TaskResponse> response = this.confluenceClient.fetchAllTasks();
        
        ArrayList<TaskEntity> tasks = new ArrayList<TaskEntity>();

        dto.results = new ArrayList<TaskDTO>();
        
        response.results.forEach((result) -> {
            TaskEntity taskEntity = this.confluenceTaskRepository.findById(result.id).orElse(new TaskEntity()); 
            this.taskMapper.convertResponseToEntityWithoutConsideringUsers(taskEntity, result);
            taskEntity.assignedTo = result.assignedTo != null ? this.confluenceUserRepository.findById(result.assignedTo).orElse(null) : null;
            taskEntity.completedBy = result.completedBy != null ? this.confluenceUserRepository.findById(result.completedBy).orElse(null) : null; 
            taskEntity.createdBy = result.createdBy != null ? this.confluenceUserRepository.findById(result.createdBy).orElse(null) : null; 
            tasks.add(taskEntity); 
            TaskDTO taskDTO = this.taskMapper.entityToDTO(taskEntity);
            dto.results.add(taskDTO);
        });

        dto.size = tasks.size();

        this.confluenceTaskRepository.saveAll(tasks);

        return dto;
    }

    public ConfluenceRootDTO<TaskDTO> getAllTasks(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<TaskEntity> taskPage = this.confluenceTaskRepository.findAll(pageable);

        ConfluenceRootDTO<TaskDTO> dto = new ConfluenceRootDTO<TaskDTO>();
        ArrayList<TaskEntity> taskEntities = new ArrayList<TaskEntity>(taskPage.getContent());
        
        dto.size = taskEntities.size();
        dto.pageNumber = pageNumber;
        dto.results = new ArrayList<TaskDTO>();
        
        taskEntities.forEach((taskEntity) -> {
            TaskDTO taskDTO = this.taskMapper.entityToDTO(taskEntity);
            dto.results.add(taskDTO);
        });

        return dto;
    }

    public TaskDTO getTaskById(String taskId)
    {
        TaskEntity taskEntity = this.confluenceTaskRepository.findById(taskId).orElse(null);
        return this.taskMapper.entityToDTO(taskEntity);
    }

}
