package com.example.confluence_api.service;

import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.confluence_api.client.ConfluenceClient;
import com.example.confluence_api.client.model.ConfluenceRootResponse;
import com.example.confluence_api.client.model.ConfluenceContentResponse;
import com.example.confluence_api.client.model.ConfluenceGroupResponse;
import com.example.confluence_api.client.model.ConfluenceSpaceResponse;
import com.example.confluence_api.client.model.ConfluenceTaskResponse;
import com.example.confluence_api.client.model.ConfluenceUserResponse;
import com.example.confluence_api.dto.ConfluenceRootDTO;
import com.example.confluence_api.dto.ConfluenceContentDTO;
import com.example.confluence_api.dto.ConfluenceGroupDTO;
import com.example.confluence_api.dto.ConfluenceSpaceDTO;
import com.example.confluence_api.dto.ConfluenceTaskDTO;
import com.example.confluence_api.dto.ConfluenceUserDTO;
import com.example.confluence_api.entity.ConfluenceContentEntity;
import com.example.confluence_api.entity.ConfluenceGroupEntity;
import com.example.confluence_api.entity.ConfluenceSpaceEntity;
import com.example.confluence_api.entity.ConfluenceTaskEntity;
import com.example.confluence_api.entity.ConfluenceUserEntity;
import com.example.confluence_api.mapper.ContentMapper;
import com.example.confluence_api.mapper.GroupMapper;
import com.example.confluence_api.mapper.SpaceMapper;
import com.example.confluence_api.mapper.TaskMapper;
import com.example.confluence_api.mapper.UserMapper;
import com.example.confluence_api.repository.ConfluenceContentRepository;
import com.example.confluence_api.repository.ConfluenceGroupRepository;
import com.example.confluence_api.repository.ConfluenceSpaceRepository;
import com.example.confluence_api.repository.ConfluenceTaskRepository;
import com.example.confluence_api.repository.ConfluenceUserRepository;

@Service
public class ConfluenceService 
{
    private final ContentMapper contentMapper; 
    private final GroupMapper groupMapper; 
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;
    private final SpaceMapper spaceMapper; 
    private final ConfluenceClient confluenceClient; 
    private final ConfluenceContentRepository confluenceContentRepository; 
    private final ConfluenceGroupRepository confluenceGroupRepository; 
    private final ConfluenceUserRepository confluenceUserRepository; 
    private final ConfluenceTaskRepository confluenceTaskRepository;
    private final ConfluenceSpaceRepository confluenceSpaceRepository;  

    public ConfluenceService(
        ContentMapper contentMapper, 
        GroupMapper groupMapper, 
        UserMapper userMapper,
        TaskMapper taskMapper,  
        SpaceMapper spaceMapper, 
        ConfluenceClient confluenceClient, 
        ConfluenceContentRepository confluenceContentRepository, 
        ConfluenceGroupRepository confluenceGroupRepository, 
        ConfluenceUserRepository confluenceUserRepository, 
        ConfluenceTaskRepository confluenceTaskRepository, 
        ConfluenceSpaceRepository confluenceSpaceRepository
    ){
        this.confluenceClient = confluenceClient; 
        this.contentMapper = contentMapper; 
        this.groupMapper = groupMapper; 
        this.userMapper = userMapper;
        this.taskMapper = taskMapper;  
        this.spaceMapper = spaceMapper;
        this.confluenceContentRepository = confluenceContentRepository; 
        this.confluenceGroupRepository = confluenceGroupRepository;
        this.confluenceUserRepository = confluenceUserRepository;
        this.confluenceTaskRepository = confluenceTaskRepository;
        this.confluenceSpaceRepository = confluenceSpaceRepository;
    }


    /* -------------------- CONTENT METHODS  -------------------- */

    public ConfluenceRootDTO<ConfluenceContentDTO> saveContents()
    {
        ConfluenceRootDTO<ConfluenceContentDTO> dto = new ConfluenceRootDTO<ConfluenceContentDTO>();
        ConfluenceRootResponse<ConfluenceContentResponse> response = this.confluenceClient.fetchAllContents();
        
        ArrayList<ConfluenceContentEntity> contents = new ArrayList<ConfluenceContentEntity>();

        dto.size = response.size; 
        dto.results = new ArrayList<ConfluenceContentDTO>();

        response.results.forEach((result) -> {
            ConfluenceContentEntity contentEntity = this.confluenceContentRepository.findById(result.id).orElse(new ConfluenceContentEntity());
            this.contentMapper.convertResponseToEntityWithoutConsideringTasks(contentEntity, result); 
            contents.add(contentEntity);
            ConfluenceContentDTO contentDTO = this.contentMapper.entityToDTO(contentEntity);
            dto.results.add(contentDTO); 
        });

        this.confluenceContentRepository.saveAll(contents);
        
        return dto; 
    }

    public ConfluenceRootDTO<ConfluenceContentDTO> getAllContents(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size); 
        Page<ConfluenceContentEntity> contentPage = this.confluenceContentRepository.findAll(pageable);

        ConfluenceRootDTO<ConfluenceContentDTO> dto = new ConfluenceRootDTO<ConfluenceContentDTO>();
        ArrayList<ConfluenceContentEntity> contentEntities = new ArrayList<ConfluenceContentEntity>(contentPage.getContent()); 
        
        dto.size = contentEntities.size();
        dto.pageNumber = pageNumber; 
        dto.results = new ArrayList<ConfluenceContentDTO>();
        
        contentEntities.forEach((contentEntity) -> {
            ConfluenceContentDTO contentDTO = this.contentMapper.entityToDTO(contentEntity);
            dto.results.add(contentDTO); 
        });

        return dto;
    }

    public ConfluenceContentDTO getContentById(String id)
    {
        ConfluenceContentEntity contentEntity = this.confluenceContentRepository.findById(id).orElse(null);
        return this.contentMapper.entityToDTO(contentEntity); 
    }


    /* -------------------- GROUP METHODS  -------------------- */

    public ConfluenceRootDTO<ConfluenceGroupDTO> saveGroups()
    {
        ConfluenceRootDTO<ConfluenceGroupDTO> dto = new ConfluenceRootDTO<ConfluenceGroupDTO>();
        ConfluenceRootResponse<ConfluenceGroupResponse> response = this.confluenceClient.fetchAllGroups();
        
        ArrayList<ConfluenceGroupEntity> groups = new ArrayList<ConfluenceGroupEntity>();

        dto.size = response.size; 
        dto.results = new ArrayList<ConfluenceGroupDTO>();

        response.results.forEach((result) -> {
            ConfluenceGroupEntity groupEntity = this.confluenceGroupRepository.findById(result.id).orElse(new ConfluenceGroupEntity()); 
            this.groupMapper.convertResponseToEntityWithoutConsideringMembers(groupEntity, result);
            groups.add(groupEntity);
            ConfluenceGroupDTO groupDTO = this.groupMapper.entityToDTO(groupEntity);
            dto.results.add(groupDTO); 
        });

        this.confluenceGroupRepository.saveAll(groups);

        return dto;
    }

    public ConfluenceRootDTO<ConfluenceGroupDTO> getAllGroups(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size); 
        Page<ConfluenceGroupEntity> groupPage = this.confluenceGroupRepository.findAll(pageable);

        ConfluenceRootDTO<ConfluenceGroupDTO> dto = new ConfluenceRootDTO<ConfluenceGroupDTO>();
        ArrayList<ConfluenceGroupEntity> groupEntities = new ArrayList<ConfluenceGroupEntity>(groupPage.getContent()); 
        
        dto.size = groupEntities.size();
        dto.pageNumber = pageNumber;
        dto.results = new ArrayList<ConfluenceGroupDTO>();
        
        groupEntities.forEach((groupEntity) -> {
            ConfluenceGroupDTO groupDTO = this.groupMapper.entityToDTO(groupEntity);
            dto.results.add(groupDTO); 
        });

        return dto; 
    }

    public ConfluenceGroupDTO getGroupById(String id)
    {
        ConfluenceGroupEntity groupEntity = this.confluenceGroupRepository.findById(id).orElse(null);
        return this.groupMapper.entityToDTO(groupEntity);
    }

    public ConfluenceRootDTO<ConfluenceUserDTO> saveGroupMembers(String groupId)
    {
        ConfluenceRootResponse<ConfluenceUserResponse> response = this.confluenceClient.fetchUsersForAGroup(groupId); 
        ConfluenceRootDTO<ConfluenceUserDTO> dto = new ConfluenceRootDTO<ConfluenceUserDTO>();
        ConfluenceGroupEntity groupEntity = this.confluenceGroupRepository.findById(groupId).orElse(null);

        dto.size = response.size;
        dto.results = new ArrayList<ConfluenceUserDTO>();

        response.results.forEach((member) -> {
            ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(member.accountId).orElse(new ConfluenceUserEntity());
            this.userMapper.convertResponseToEntityWithoutConsideringTasks(userEntity, member); 
            this.confluenceUserRepository.save(userEntity);
            ConfluenceUserDTO userDTO = this.userMapper.entityToDTO(userEntity);
            dto.results.add(userDTO);
            groupEntity.members.add(userEntity); 
        });

        this.confluenceGroupRepository.save(groupEntity); 
        return dto; 
    }

    public ConfluenceRootDTO<ConfluenceUserDTO> getGroupMembers(String groupId)
    {
        ConfluenceRootDTO<ConfluenceUserDTO> dto = new ConfluenceRootDTO<ConfluenceUserDTO>();
        ConfluenceGroupEntity groupEntity = this.confluenceGroupRepository.findById(groupId).orElse(null);

        try
        {
            dto.size = groupEntity.members.size();
            dto.results = new ArrayList<ConfluenceUserDTO>();
            groupEntity.members.forEach((member) -> {
                ConfluenceUserDTO userDTO = this.userMapper.entityToDTO(member);
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

    public ConfluenceRootDTO<ConfluenceUserDTO> saveUsers()
    {
        ConfluenceRootDTO<ConfluenceUserDTO> dto = new ConfluenceRootDTO<ConfluenceUserDTO>();
        ConfluenceRootResponse<ConfluenceGroupResponse> groups = this.confluenceClient.fetchAllGroups();

        HashSet<ConfluenceUserEntity> users = new HashSet<ConfluenceUserEntity>();
        
        HashSet<ConfluenceUserDTO> setOfUsers = new HashSet<ConfluenceUserDTO>();

        groups.results.stream().forEach((group) -> {
            ConfluenceRootResponse<ConfluenceUserResponse> groupMembers = this.confluenceClient.fetchUsersForAGroup(group.id);
            groupMembers.results.stream().forEach((member) -> {
                ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(member.accountId).orElse(new ConfluenceUserEntity());
                this.userMapper.convertResponseToEntityWithoutConsideringTasks(userEntity, member);
                users.add(userEntity); 
                ConfluenceUserDTO userDTO = this.userMapper.entityToDTO(userEntity);
                setOfUsers.add(userDTO);
            });
        });

        dto.size = users.size();
        dto.results = new ArrayList<ConfluenceUserDTO>(setOfUsers);

        this.confluenceUserRepository.saveAll(users);

        return dto; 
    }

    public ConfluenceRootDTO<ConfluenceUserDTO> getAllUsers(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<ConfluenceUserEntity> userPage = this.confluenceUserRepository.findAll(pageable);

        ConfluenceRootDTO<ConfluenceUserDTO> dto = new ConfluenceRootDTO<ConfluenceUserDTO>();
        ArrayList<ConfluenceUserEntity> userEntities = new ArrayList<ConfluenceUserEntity>(userPage.getContent());
        
        dto.size = userEntities.size();
        dto.pageNumber = pageNumber;
        dto.results = new ArrayList<ConfluenceUserDTO>();
        
        userEntities.forEach((userEntity) -> {
            ConfluenceUserDTO userDTO = this.userMapper.entityToDTO(userEntity);
            dto.results.add(userDTO);
        });

        return dto;
    }

    public ConfluenceUserDTO getUserById(String userId)
    {
        ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);
        return this.userMapper.entityToDTO(userEntity);
    }

    public ConfluenceRootDTO<ConfluenceTaskDTO> getUserCompletedTasks(String userId)
    {
        ConfluenceRootDTO<ConfluenceTaskDTO> dto = new ConfluenceRootDTO<ConfluenceTaskDTO>();
        ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);

        try
        {
            dto.size = userEntity.completedTasks.size();
            dto.results = new ArrayList<ConfluenceTaskDTO>();
            userEntity.completedTasks.forEach((task) -> {
                ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                dto.results.add(taskDTO);
            });
        }
        catch(NullPointerException e)
        {
            dto.message = "Cannot find user that matches userId: " + userId;
        }

        return dto;
    }

    public ConfluenceRootDTO<ConfluenceTaskDTO> getUserAssignedTasks(String userId)
    {
        ConfluenceRootDTO<ConfluenceTaskDTO> dto = new ConfluenceRootDTO<ConfluenceTaskDTO>();
        ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);

        try
        {
            dto.size = userEntity.assignedTasks.size();
            dto.results = new ArrayList<ConfluenceTaskDTO>();
            userEntity.assignedTasks.forEach((task) -> {
                ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                dto.results.add(taskDTO);
            });
        }
        catch(NullPointerException e)
        {
            dto.message = "Cannot find user that matches userId: " + userId;
        }

        return dto;
    }

    public ConfluenceRootDTO<ConfluenceTaskDTO> getUserCreatedTasks(String userId)
    {
        ConfluenceRootDTO<ConfluenceTaskDTO> dto = new ConfluenceRootDTO<ConfluenceTaskDTO>();
        ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);

        try
        {
            dto.size = userEntity.createdTasks.size();
            dto.results = new ArrayList<ConfluenceTaskDTO>();
            userEntity.createdTasks.forEach((task) -> {
                ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                dto.results.add(taskDTO);
            });
        }
        catch(NullPointerException e)
        {
            dto.message = "Cannot find user that matches userId: " + userId;
        }

        return dto;
    }

    public ConfluenceRootDTO<ConfluenceSpaceDTO> getUserSpaces(String userId)
    {
        ConfluenceRootDTO<ConfluenceSpaceDTO> dto = new ConfluenceRootDTO<ConfluenceSpaceDTO>();
        ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);

        try
        {
            dto.size = userEntity.spaces.size();
            dto.results = new ArrayList<ConfluenceSpaceDTO>();
            userEntity.spaces.forEach((space) -> {
                ConfluenceSpaceDTO spaceDTO = this.spaceMapper.entityToDTO(space);
                dto.results.add(spaceDTO);
            });
        }
        catch(NullPointerException e)
        {
            dto.message = "Cannot find user that matches userId: " + userId;
        }

        return dto;
    }

    /* -------------------- TASK METHODS  -------------------- */

    public ConfluenceRootDTO<ConfluenceTaskDTO> saveTasks()
    {
        ConfluenceRootDTO<ConfluenceTaskDTO> dto = new ConfluenceRootDTO<ConfluenceTaskDTO>();
        ConfluenceRootResponse<ConfluenceTaskResponse> response = this.confluenceClient.fetchAllTasks();
        
        ArrayList<ConfluenceTaskEntity> tasks = new ArrayList<ConfluenceTaskEntity>();

        dto.results = new ArrayList<ConfluenceTaskDTO>();
        
        response.results.forEach((result) -> {
            ConfluenceTaskEntity taskEntity = this.confluenceTaskRepository.findById(result.id).orElse(new ConfluenceTaskEntity()); 
            this.taskMapper.convertResponseToEntityWithoutConsideringRelations(taskEntity, result);
            taskEntity.assignedTo = result.assignedTo != null ? this.confluenceUserRepository.findById(result.assignedTo).orElse(null) : null;
            taskEntity.completedBy = result.completedBy != null ? this.confluenceUserRepository.findById(result.completedBy).orElse(null) : null; 
            taskEntity.createdBy = result.createdBy != null ? this.confluenceUserRepository.findById(result.createdBy).orElse(null) : null; 
            tasks.add(taskEntity); 
            taskEntity.page = result.pageId != null ? this.confluenceContentRepository.findById(result.pageId).orElse(null) : null; 
            taskEntity.space = result.spaceId != null ? this.confluenceSpaceRepository.findById(result.spaceId).orElse(null) : null;
            ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(taskEntity);
            dto.results.add(taskDTO);
        });

        dto.size = tasks.size();

        this.confluenceTaskRepository.saveAll(tasks);

        return dto;
    }

    public ConfluenceRootDTO<ConfluenceTaskDTO> getAllTasks(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<ConfluenceTaskEntity> taskPage = this.confluenceTaskRepository.findAll(pageable);

        ConfluenceRootDTO<ConfluenceTaskDTO> dto = new ConfluenceRootDTO<ConfluenceTaskDTO>();
        ArrayList<ConfluenceTaskEntity> taskEntities = new ArrayList<ConfluenceTaskEntity>(taskPage.getContent());
        
        dto.size = taskEntities.size();
        dto.pageNumber = pageNumber;
        dto.results = new ArrayList<ConfluenceTaskDTO>();
        
        taskEntities.forEach((taskEntity) -> {
            ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(taskEntity);
            dto.results.add(taskDTO);
        });

        return dto;
    }

    public ConfluenceTaskDTO getTaskById(String taskId)
    {
        ConfluenceTaskEntity taskEntity = this.confluenceTaskRepository.findById(taskId).orElse(null);
        return this.taskMapper.entityToDTO(taskEntity);
    }


    /* -------------------- SPACE METHODS  -------------------- */
    
    public ConfluenceRootDTO<ConfluenceSpaceDTO> saveSpaces()
    {
        ConfluenceRootDTO<ConfluenceSpaceDTO> dto = new ConfluenceRootDTO<ConfluenceSpaceDTO>();
        ConfluenceRootResponse<ConfluenceSpaceResponse> response = this.confluenceClient.fetchAllSpaces();
        
        ArrayList<ConfluenceSpaceEntity> spaces = new ArrayList<ConfluenceSpaceEntity>();
        
        dto.results = new ArrayList<ConfluenceSpaceDTO>();

        response.results.forEach((result) -> {
            ConfluenceSpaceEntity spaceEntity = this.confluenceSpaceRepository.findById(result.id).orElse(new ConfluenceSpaceEntity()); 
            this.spaceMapper.convertResponseToEntityWithoutConsideringRelations(spaceEntity, result);
            spaceEntity.author = result.authorId != null ? this.confluenceUserRepository.findById(result.authorId).orElse(null) : null; 
            spaceEntity.homepage = result.homepageId != null ? this.confluenceContentRepository.findById(result.homepageId).orElse(null) : null;
            spaces.add(spaceEntity);
            ConfluenceSpaceDTO spaceDTO = this.spaceMapper.entityToDTO(spaceEntity);
            dto.results.add(spaceDTO);
        });

        dto.size = spaces.size();
        
        this.confluenceSpaceRepository.saveAll(spaces);
        
        return dto;
    }

    public ConfluenceRootDTO<ConfluenceSpaceDTO> getAllSpaces(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<ConfluenceSpaceEntity> spacePage = this.confluenceSpaceRepository.findAll(pageable);

        ConfluenceRootDTO<ConfluenceSpaceDTO> dto = new ConfluenceRootDTO<ConfluenceSpaceDTO>();
        ArrayList<ConfluenceSpaceEntity> spaceEntities = new ArrayList<ConfluenceSpaceEntity>(spacePage.getContent());
        
        dto.size = spaceEntities.size();
        dto.pageNumber = pageNumber;
        dto.results = new ArrayList<ConfluenceSpaceDTO>();
        
        spaceEntities.forEach((spaceEntity) -> {
            ConfluenceSpaceDTO spaceDTO = this.spaceMapper.entityToDTO(spaceEntity);
            dto.results.add(spaceDTO);
        });

        return dto;
    }

    public ConfluenceSpaceDTO getSpaceById(String spaceId)
    {
        ConfluenceSpaceEntity spaceEntity = this.confluenceSpaceRepository.findById(spaceId).orElse(null);
        return this.spaceMapper.entityToDTO(spaceEntity);
    }
}
