package com.example.confluence_api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.confluence_api.client.ConfluenceClient;
import com.example.confluence_api.client.model.ConfluenceContentResponse;
import com.example.confluence_api.client.model.ConfluenceContentVersionResponse;
import com.example.confluence_api.client.model.ConfluenceGroupResponse;
import com.example.confluence_api.client.model.ConfluencePageRootResponse;
import com.example.confluence_api.client.model.ConfluenceRootResponse;
import com.example.confluence_api.client.model.ConfluenceSpaceResponse;
import com.example.confluence_api.client.model.ConfluenceTaskResponse;
import com.example.confluence_api.client.model.ConfluenceUserResponse;
import com.example.confluence_api.dto.ConfluenceContentDTO;
import com.example.confluence_api.dto.ConfluenceContentVersionDTO;
import com.example.confluence_api.dto.ConfluenceGroupDTO;
import com.example.confluence_api.dto.ConfluenceResultRootDTO;
import com.example.confluence_api.dto.ConfluenceResultsRootDTO;
import com.example.confluence_api.dto.ConfluenceSpaceDTO;
import com.example.confluence_api.dto.ConfluenceTaskDTO;
import com.example.confluence_api.dto.ConfluenceUserDTO;
import com.example.confluence_api.dto.ConfluenceUserStatisticsDTO;
import com.example.confluence_api.repository.ConfluenceContentRepository;
import com.example.confluence_api.repository.ConfluenceContentVersionRepository;
import com.example.confluence_api.repository.ConfluenceGroupRepository;
import com.example.confluence_api.repository.ConfluenceSpaceRepository;
import com.example.confluence_api.repository.ConfluenceTaskRepository;
import com.example.confluence_api.repository.ConfluenceUserRepository;
import com.example.confluence_api.mapper.ContentMapper;
import com.example.confluence_api.mapper.ContentVersionMapper;
import com.example.confluence_api.mapper.GroupMapper;
import com.example.confluence_api.mapper.SpaceMapper;
import com.example.confluence_api.mapper.TaskMapper;
import com.example.confluence_api.mapper.UserMapper;
import com.example.confluence_api.model.ConfluenceContentEntity;
import com.example.confluence_api.model.ConfluenceContentVersionEntity;
import com.example.confluence_api.model.ConfluenceGroupEntity;
import com.example.confluence_api.model.ConfluenceSpaceEntity;
import com.example.confluence_api.model.ConfluenceTaskEntity;
import com.example.confluence_api.model.ConfluenceUserEntity;
import com.example.confluence_api.util.ConfluenceContentBodyProcessor;
import com.example.confluence_api.util.DateConversion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

@Service
public class ConfluenceService 
{
    private final ContentMapper contentMapper; 
    private final GroupMapper groupMapper; 
    private final UserMapper userMapper;
    private final TaskMapper taskMapper;
    private final SpaceMapper spaceMapper; 
    private final ContentVersionMapper contentVersionMapper; 
    private final ConfluenceClient confluenceClient; 
    private final ConfluenceContentRepository confluenceContentRepository; 
    private final ConfluenceGroupRepository confluenceGroupRepository; 
    private final ConfluenceUserRepository confluenceUserRepository; 
    private final ConfluenceTaskRepository confluenceTaskRepository;
    private final ConfluenceSpaceRepository confluenceSpaceRepository;  
    private final ConfluenceContentVersionRepository confluenceContentVersionRepository; 

    public ConfluenceService(
        ContentMapper contentMapper, 
        GroupMapper groupMapper, 
        UserMapper userMapper,
        TaskMapper taskMapper,  
        SpaceMapper spaceMapper, 
        ContentVersionMapper contentVersionMapper, 
        ConfluenceClient confluenceClient, 
        ConfluenceContentRepository confluenceContentRepository, 
        ConfluenceGroupRepository confluenceGroupRepository, 
        ConfluenceUserRepository confluenceUserRepository, 
        ConfluenceTaskRepository confluenceTaskRepository, 
        ConfluenceSpaceRepository confluenceSpaceRepository, 
        ConfluenceContentVersionRepository confluenceContentVersionRepository  
    ){
        this.confluenceClient = confluenceClient; 
        this.contentMapper = contentMapper; 
        this.groupMapper = groupMapper; 
        this.userMapper = userMapper;
        this.taskMapper = taskMapper;  
        this.spaceMapper = spaceMapper;
        this.contentVersionMapper = contentVersionMapper; 
        this.confluenceContentRepository = confluenceContentRepository; 
        this.confluenceGroupRepository = confluenceGroupRepository;
        this.confluenceUserRepository = confluenceUserRepository;
        this.confluenceTaskRepository = confluenceTaskRepository;
        this.confluenceSpaceRepository = confluenceSpaceRepository;
        this.confluenceContentVersionRepository = confluenceContentVersionRepository;
    }


    /* -------------------- CONTENT METHODS  -------------------- */

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentDTO>> saveContents()
    {
        ConfluenceResultsRootDTO<ConfluenceContentDTO> dto = new ConfluenceResultsRootDTO<ConfluenceContentDTO>();
        ConfluenceRootResponse<ConfluenceContentResponse> response = this.confluenceClient.fetchAllContents();
        
        ArrayList<ConfluenceContentEntity> contents = new ArrayList<ConfluenceContentEntity>();

        dto.setResults(new ArrayList<ConfluenceContentDTO>());

        response.getResults().forEach((result) -> {
            ConfluenceContentEntity contentEntity = this.confluenceContentRepository.findById(result.getId()).orElse(new ConfluenceContentEntity());
            this.contentMapper.convertResponseToEntityWithoutConsideringRelations(contentEntity, result); 
            contents.add(contentEntity);
            ConfluenceContentDTO contentDTO = this.contentMapper.entityToDTO(contentEntity);
            dto.getResults().add(contentDTO); 
        });

        this.confluenceContentRepository.saveAll(contents);

        dto.setSize(dto.getResults().size());
        dto.setStatus(HttpStatus.CREATED);
        
        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentDTO>>(dto, dto.getStatus()); 
    }

    public ConfluenceResultsRootDTO<ConfluenceContentDTO> getAllContents(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size); 
        Page<ConfluenceContentEntity> contentPage = this.confluenceContentRepository.findAll(pageable);

        ConfluenceResultsRootDTO<ConfluenceContentDTO> dto = new ConfluenceResultsRootDTO<ConfluenceContentDTO>();
        ArrayList<ConfluenceContentEntity> contentEntities = new ArrayList<ConfluenceContentEntity>(contentPage.getContent()); 
        
        dto.setPageNumber(pageNumber);
        dto.setResults(new ArrayList<ConfluenceContentDTO>());
        
        contentEntities.forEach((contentEntity) -> {
            ConfluenceContentDTO contentDTO = this.contentMapper.entityToDTO(contentEntity);
            dto.getResults().add(contentDTO); 
        });

        dto.setSize(dto.getResults().size());
        dto.setStatus(HttpStatus.OK);

        return dto;
    }

    public ResponseEntity<ConfluenceResultRootDTO<ConfluenceContentDTO>> getContentById(String id)
    {
        ConfluenceContentEntity contentEntity = this.confluenceContentRepository.findById(id).orElse(null);
        ConfluenceResultRootDTO<ConfluenceContentDTO> dto = new ConfluenceResultRootDTO<ConfluenceContentDTO>(); 
        if (contentEntity == null) 
        {
            dto.setStatus(HttpStatus.NOT_FOUND);
            dto.setMessage("Couldn't find Content with id " + id);
        }
        else 
        {
            dto.setStatus(HttpStatus.OK);
            dto.setResult(this.contentMapper.entityToDTO(contentEntity));
        }
        return new ResponseEntity<ConfluenceResultRootDTO<ConfluenceContentDTO>>(dto, dto.getStatus()); 
    }

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentVersionDTO>> getContentVersions(String id)
    {
        ConfluenceResultsRootDTO<ConfluenceContentVersionDTO> dto = new ConfluenceResultsRootDTO<ConfluenceContentVersionDTO>();
        ConfluenceContentEntity contentEntity = this.confluenceContentRepository.findById(id).orElse(null); 

        try
        {
            dto.setStatus(HttpStatus.OK);
            dto.setResults(new ArrayList<ConfluenceContentVersionDTO>());
            contentEntity.getVersions().forEach((version) -> {
                ConfluenceContentVersionDTO versionDTO = this.contentVersionMapper.entityToDTO(version); 
                dto.getResults().add(versionDTO);
            });
        }
        catch(NullPointerException e)
        {
            dto.setStatus(HttpStatus.NOT_FOUND);
            dto.setMessage("Couldn't find Content with id " + id);
        }

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentVersionDTO>>(dto, dto.getStatus()); 
    }
    

    /* -------------------- CONTENT VERSION METHODS  -------------------- */

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentVersionDTO>> saveContentVersions()
    {
        ConfluenceResultsRootDTO<ConfluenceContentVersionDTO> dto = new ConfluenceResultsRootDTO<ConfluenceContentVersionDTO>();
        ConfluenceRootResponse<ConfluenceContentResponse> contentResponse = this.confluenceClient.fetchAllContents(); 

        ArrayList<ConfluenceContentVersionEntity> versionEntities = new ArrayList<ConfluenceContentVersionEntity>();
        ArrayList<ConfluenceContentEntity> contentEntities = new ArrayList<ConfluenceContentEntity>();
        
        dto.setResults(new ArrayList<ConfluenceContentVersionDTO>());
        

        contentResponse.getResults().stream().forEach((contentResult) -> {
            ConfluenceContentEntity contentEntity = this.confluenceContentRepository.findById(contentResult.getId()).orElse(new ConfluenceContentEntity()); 
            this.contentMapper.convertResponseToEntityWithoutConsideringRelations(contentEntity, contentResult);
            contentEntities.add(contentEntity);
        });

        this.confluenceContentRepository.saveAll(contentEntities);

        contentResponse.getResults().stream().forEach((contentResult) -> {
            String contentId = contentResult.getId(); 
            ConfluenceRootResponse<ConfluenceContentVersionResponse> versionResponse = this.confluenceClient.fetchVersionsForAContent(contentId); 

            ConfluenceContentResponse[] expandedContentsArray = new ConfluenceContentResponse[versionResponse.getResults().size() + 1];

            versionResponse.getResults().stream().forEach((result) -> {
                int versionNumber = result.getNumber(); 
                ConfluenceContentResponse expandedContent = this.confluenceClient.fetchExpandedContentById(
                    contentId, versionNumber
                );
                expandedContentsArray[versionNumber] = expandedContent; 
            });
            
            versionResponse.getResults().stream().forEach((result) -> {
                int versionNumber = result.getNumber(); 
                ConfluenceContentVersionEntity versionEntity = this.confluenceContentVersionRepository.findByContentIdAndVersionNumber(
                    contentId, versionNumber               
                ).orElse(new ConfluenceContentVersionEntity());

                this.contentVersionMapper.convertResponseToEntityWithoutConsideringRelations(versionEntity, result);

                versionEntity.setContent(contentId != null ? this.confluenceContentRepository.findById(contentId).orElse(null) : null);

                if (result != null && result.getBy() != null) 
                {
                    String byId = result.getBy().getAccountId(); 
                    versionEntity.setBy(byId != null ? this.confluenceUserRepository.findById(byId).orElse(null) : null);
                }
                
                ConfluenceContentResponse expandedContent = expandedContentsArray[versionNumber]; 

                int numberOfLines = ConfluenceContentBodyProcessor.countLines(
                    expandedContent.getBody().getStorage().getValue()
                ); 

                int addedLines = numberOfLines; 

                if (versionNumber > 1) 
                {
                    ConfluenceContentResponse expandedContentPreviousVersion = expandedContentsArray[versionNumber - 1];
                    int previousLines = ConfluenceContentBodyProcessor.countLines(
                        expandedContentPreviousVersion.getBody().getStorage().getValue()
                    ); 
                    addedLines = numberOfLines - previousLines;
                }

                versionEntity.setAddedLines(addedLines);
                versionEntity.setTotalLines(numberOfLines);
                
                versionEntities.add(versionEntity); 
                ConfluenceContentVersionDTO versionDTO = this.contentVersionMapper.entityToDTO(versionEntity); 
                dto.getResults().add(versionDTO);
            });
        });
        
        this.confluenceContentVersionRepository.saveAll(versionEntities);
        
        dto.setSize(dto.getResults().size());
        dto.setStatus(HttpStatus.CREATED);

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentVersionDTO>>(dto, dto.getStatus());
    }


    /* -------------------- GROUP METHODS  -------------------- */

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceGroupDTO>> saveGroups()
    {
        ConfluenceResultsRootDTO<ConfluenceGroupDTO> dto = new ConfluenceResultsRootDTO<ConfluenceGroupDTO>();
        ConfluenceRootResponse<ConfluenceGroupResponse> response = this.confluenceClient.fetchAllGroups();
        
        ArrayList<ConfluenceGroupEntity> groups = new ArrayList<ConfluenceGroupEntity>();

        dto.setResults(new ArrayList<ConfluenceGroupDTO>());

        response.getResults().forEach((result) -> {
            ConfluenceGroupEntity groupEntity = this.confluenceGroupRepository.findById(result.getId()).orElse(new ConfluenceGroupEntity()); 
            this.groupMapper.convertResponseToEntityWithoutConsideringMembers(groupEntity, result);
            groups.add(groupEntity);
            ConfluenceGroupDTO groupDTO = this.groupMapper.entityToDTO(groupEntity);
            dto.getResults().add(groupDTO); 
        });

        this.confluenceGroupRepository.saveAll(groups);

        dto.setSize(dto.getResults().size());
        dto.setStatus(HttpStatus.CREATED);

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceGroupDTO>>(dto, dto.getStatus());
    }

    public ConfluenceResultsRootDTO<ConfluenceGroupDTO> getAllGroups(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size); 
        Page<ConfluenceGroupEntity> groupPage = this.confluenceGroupRepository.findAll(pageable);

        ConfluenceResultsRootDTO<ConfluenceGroupDTO> dto = new ConfluenceResultsRootDTO<ConfluenceGroupDTO>();
        ArrayList<ConfluenceGroupEntity> groupEntities = new ArrayList<ConfluenceGroupEntity>(groupPage.getContent()); 
        
        dto.setPageNumber(pageNumber);
        dto.setResults(new ArrayList<ConfluenceGroupDTO>());
        
        groupEntities.forEach((groupEntity) -> {
            ConfluenceGroupDTO groupDTO = this.groupMapper.entityToDTO(groupEntity);
            dto.getResults().add(groupDTO); 
        });
        
        dto.setSize(dto.getResults().size());
        dto.setStatus(HttpStatus.OK);

        return dto; 
    }

    public ResponseEntity<ConfluenceResultRootDTO<ConfluenceGroupDTO>> getGroupById(String id)
    {
        ConfluenceGroupEntity groupEntity = this.confluenceGroupRepository.findById(id).orElse(null);
        ConfluenceResultRootDTO<ConfluenceGroupDTO> dto = new ConfluenceResultRootDTO<ConfluenceGroupDTO>(); 
        if (groupEntity == null)
        {
            dto.setStatus(HttpStatus.NOT_FOUND);
            dto.setMessage("Couldn't find group with id: " + id);
        }
        else 
        {
            dto.setStatus(HttpStatus.OK);
            dto.setResult(this.groupMapper.entityToDTO(groupEntity));
        }
        return new ResponseEntity<ConfluenceResultRootDTO<ConfluenceGroupDTO>>(dto, dto.getStatus());
    }

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceUserDTO>> saveGroupMembers(String groupId)
    {
        ConfluenceRootResponse<ConfluenceUserResponse> response = this.confluenceClient.fetchUsersForAGroup(groupId); 
        ConfluenceResultsRootDTO<ConfluenceUserDTO> dto = new ConfluenceResultsRootDTO<ConfluenceUserDTO>();
        ConfluenceGroupEntity groupEntity = this.confluenceGroupRepository.findById(groupId).orElse(null);

        dto.setResults(new ArrayList<ConfluenceUserDTO>());

        response.getResults().forEach((member) -> {
            ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(member.getAccountId()).orElse(new ConfluenceUserEntity());
            this.userMapper.convertResponseToEntityWithoutConsideringTasks(userEntity, member); 
            this.confluenceUserRepository.save(userEntity);
            ConfluenceUserDTO userDTO = this.userMapper.entityToDTO(userEntity);
            dto.getResults().add(userDTO);
            groupEntity.getMembers().add(userEntity); 
        });

        this.confluenceGroupRepository.save(groupEntity); 

        dto.setSize(dto.getResults().size());
        dto.setStatus(HttpStatus.CREATED);

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceUserDTO>>(dto, dto.getStatus()); 
    }

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceUserDTO>> getGroupMembers(String groupId)
    {
        ConfluenceResultsRootDTO<ConfluenceUserDTO> dto = new ConfluenceResultsRootDTO<ConfluenceUserDTO>();
        ConfluenceGroupEntity groupEntity = this.confluenceGroupRepository.findById(groupId).orElse(null);

        try
        {
            dto.setStatus(HttpStatus.OK);
            dto.setSize(groupEntity.getMembers().size());
            dto.setResults(new ArrayList<ConfluenceUserDTO>());
            groupEntity.getMembers().forEach((member) -> {
                ConfluenceUserDTO userDTO = this.userMapper.entityToDTO(member);
                dto.getResults().add(userDTO);
            });
        }
        catch(NullPointerException e)
        {
            dto.setStatus(HttpStatus.NOT_FOUND);
            dto.setMessage("Couldn't find group that matches groupId: " + groupId); 
        }

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceUserDTO>>(dto, dto.getStatus()); 
    }

    /* -------------------- USER METHODS  -------------------- */

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceUserDTO>> saveUsers()
    {
        ConfluenceResultsRootDTO<ConfluenceUserDTO> dto = new ConfluenceResultsRootDTO<ConfluenceUserDTO>();
        ConfluenceRootResponse<ConfluenceGroupResponse> groups = this.confluenceClient.fetchAllGroups();

        HashSet<ConfluenceUserEntity> users = new HashSet<ConfluenceUserEntity>();
        
        HashSet<ConfluenceUserDTO> setOfUsers = new HashSet<ConfluenceUserDTO>();

        groups.getResults().stream().forEach((group) -> {
            ConfluenceRootResponse<ConfluenceUserResponse> groupMembers = this.confluenceClient.fetchUsersForAGroup(group.getId());
            groupMembers.getResults().stream().forEach((member) -> {
                ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(member.getAccountId()).orElse(new ConfluenceUserEntity());
                this.userMapper.convertResponseToEntityWithoutConsideringTasks(userEntity, member);
                users.add(userEntity); 
                ConfluenceUserDTO userDTO = this.userMapper.entityToDTO(userEntity);
                setOfUsers.add(userDTO);
            });
        });

        dto.setResults(new ArrayList<ConfluenceUserDTO>(setOfUsers));
        dto.setSize(dto.getResults().size());
        dto.setStatus(HttpStatus.CREATED);

        this.confluenceUserRepository.saveAll(users);

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceUserDTO>>(dto, dto.getStatus()); 
    }

    public ConfluenceResultsRootDTO<ConfluenceUserDTO> getAllUsers(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<ConfluenceUserEntity> userPage = this.confluenceUserRepository.findAll(pageable);

        ConfluenceResultsRootDTO<ConfluenceUserDTO> dto = new ConfluenceResultsRootDTO<ConfluenceUserDTO>();
        ArrayList<ConfluenceUserEntity> userEntities = new ArrayList<ConfluenceUserEntity>(userPage.getContent());
        
        dto.setPageNumber(pageNumber);
        dto.setResults(new ArrayList<ConfluenceUserDTO>());
        
        userEntities.forEach((userEntity) -> {
            ConfluenceUserDTO userDTO = this.userMapper.entityToDTO(userEntity);
            dto.getResults().add(userDTO);
        });

        dto.setSize(dto.getResults().size());
        dto.setStatus(HttpStatus.CREATED);

        return dto;
    }

    public ResponseEntity<ConfluenceResultRootDTO<ConfluenceUserDTO>> getUserById(String userId)
    {
        ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);
        ConfluenceResultRootDTO<ConfluenceUserDTO> dto = new ConfluenceResultRootDTO<ConfluenceUserDTO>();
        if (userEntity == null)
        {
            dto.setStatus(HttpStatus.NOT_FOUND);
            dto.setMessage("Couldn't find user with id: " + userId);
        }
        else 
        {
            dto.setStatus(HttpStatus.OK);
            dto.setResult(this.userMapper.entityToDTO(userEntity));
        }
        return new ResponseEntity<ConfluenceResultRootDTO<ConfluenceUserDTO>>(dto, dto.getStatus());
    }

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceTaskDTO>> getUserCompletedTasks(
        String userId, LocalDate completedDate, LocalDate startDate, LocalDate endDate
    ){
        ConfluenceResultsRootDTO<ConfluenceTaskDTO> dto = new ConfluenceResultsRootDTO<ConfluenceTaskDTO>(); 
        ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);

        try
        {
            dto.setStatus(HttpStatus.OK);
            dto.setResults(new ArrayList<ConfluenceTaskDTO>());
            
            if (completedDate == null) 
            {
                if (startDate != null && endDate != null) 
                {
                    userEntity.getCompletedTasks().forEach((task) -> {
                        if (task.completedAt() != null)
                        {                
                            LocalDate taskCompletionDate = DateConversion.stringToLocalDate(task.completedAt());     
                            if (taskCompletionDate.isAfter(startDate) && taskCompletionDate.isBefore(endDate))
                            {
                                ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                                dto.getResults().add(taskDTO);
                            }   
                        }
                    });     
                }
                else if (startDate != null) 
                {
                    userEntity.getCompletedTasks().forEach((task) -> {
                        if (task.completedAt() != null)
                        {                
                            LocalDate taskCompletionDate = DateConversion.stringToLocalDate(task.completedAt());     
                            if (taskCompletionDate.isAfter(startDate))
                            {
                                ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                                dto.getResults().add(taskDTO);
                            }   
                        }
                    }); 
                }
                else if(endDate != null)
                {
                    userEntity.getCompletedTasks().forEach((task) -> {
                        if (task.completedAt() != null)
                        {                
                            LocalDate taskCompletionDate = DateConversion.stringToLocalDate(task.completedAt());     
                            if (taskCompletionDate.isBefore(endDate))
                            {
                                ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                                dto.getResults().add(taskDTO);
                            }   
                        }
                    }); 
                }
                else 
                {
                    userEntity.getCompletedTasks().forEach((task) -> {
                        ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                        dto.getResults().add(taskDTO);
                    });
                }
            }
            else 
            {
                userEntity.getCompletedTasks().forEach((task) -> {
                    if (task.completedAt() != null) 
                    {                
                        LocalDate taskCompletionDate = DateConversion.stringToLocalDate(task.completedAt());     
                        if (taskCompletionDate.isEqual(completedDate))
                        {
                            ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                            dto.getResults().add(taskDTO);
                        }   
                    }
                }); 
            }
            
            dto.setSize(dto.getResults().size());
        }
        catch(NullPointerException e)
        {
            dto.setStatus(HttpStatus.NOT_FOUND);
            dto.setMessage("Couldn't find user that matches userId: " + userId);
        }

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceTaskDTO>>(dto, dto.getStatus());
    }


    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceTaskDTO>> getUserAssignedTasks(String userId)
    {
        ConfluenceResultsRootDTO<ConfluenceTaskDTO> dto = new ConfluenceResultsRootDTO<ConfluenceTaskDTO>();
        ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);

        try
        {
            dto.setStatus(HttpStatus.OK);
            dto.setSize(userEntity.getAssignedTasks().size());
            dto.setResults(new ArrayList<ConfluenceTaskDTO>());
            userEntity.getAssignedTasks().forEach((task) -> {
                ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                dto.getResults().add(taskDTO);
            });
        }
        catch(NullPointerException e)
        { 
            dto.setStatus(HttpStatus.NOT_FOUND);
            dto.setMessage("Couldn't find user that matches userId: " + userId);
        }

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceTaskDTO>>(dto, dto.getStatus());
    }

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceTaskDTO>> getUserCreatedTasks(
        String userId, LocalDate createdDate, LocalDate startDate, LocalDate endDate
    ){
        ConfluenceResultsRootDTO<ConfluenceTaskDTO> dto = new ConfluenceResultsRootDTO<ConfluenceTaskDTO>(); 
        ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);

        try
        {
            dto.setStatus(HttpStatus.OK);
            dto.setResults(new ArrayList<ConfluenceTaskDTO>());

            if (createdDate == null) 
            {
                if (startDate != null && endDate != null)  
                {
                    userEntity.getCreatedTasks().forEach((task) -> {
                        if (task.createdAt() != null)
                        {                
                            LocalDate taskCreationDate = DateConversion.stringToLocalDate(task.createdAt());     
                            if (taskCreationDate.isAfter(startDate) && taskCreationDate.isBefore(endDate))
                            {
                                ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                                dto.getResults().add(taskDTO);
                            }   
                        }
                    });  
                }
                else if (startDate != null) 
                {
                    userEntity.getCreatedTasks().forEach((task) -> {
                        if (task.createdAt() != null)
                        {                
                            LocalDate taskCreationDate = DateConversion.stringToLocalDate(task.createdAt());     
                            if (taskCreationDate.isAfter(startDate))
                            {
                                ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                                dto.getResults().add(taskDTO);
                            }   
                        }
                    });  
                }
                else if (endDate != null) 
                {
                    userEntity.getCreatedTasks().forEach((task) -> {
                        if (task.createdAt() != null)
                        {                
                            LocalDate taskCreationDate = DateConversion.stringToLocalDate(task.createdAt());     
                            if (taskCreationDate.isBefore(endDate))
                            {
                                ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                                dto.getResults().add(taskDTO);
                            }   
                        }
                    });  
                }
                else 
                {
                    userEntity.getCreatedTasks().forEach((task) -> {
                        ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                        dto.getResults().add(taskDTO);
                    });
                }
            }
            else 
            {
                userEntity.getCreatedTasks().forEach((task) -> {
                    if (task.createdAt() != null) 
                    {                
                        LocalDate taskCreationDate = DateConversion.stringToLocalDate(task.createdAt());     
                        if (taskCreationDate.isEqual(createdDate))
                        {
                            ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(task);
                            dto.getResults().add(taskDTO);
                        }   
                    }
                });  
            }

            dto.setSize(dto.getResults().size());
        }
        catch(NullPointerException e)
        {
            dto.setStatus(HttpStatus.NOT_FOUND);
            dto.setMessage("Couldn't find user that matches userId: " + userId);
        }

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceTaskDTO>>(dto, dto.getStatus());
    }

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceSpaceDTO>> getUserSpaces(String userId)
    {
        ConfluenceResultsRootDTO<ConfluenceSpaceDTO> dto = new ConfluenceResultsRootDTO<ConfluenceSpaceDTO>();
        ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);

        try
        {
            dto.setStatus(HttpStatus.OK);
            dto.setSize(userEntity.getSpaces().size());
            dto.setResults(new ArrayList<ConfluenceSpaceDTO>());
            userEntity.getSpaces().forEach((space) -> {
                ConfluenceSpaceDTO spaceDTO = this.spaceMapper.entityToDTO(space);
                dto.getResults().add(spaceDTO);
            });
        }
        catch(NullPointerException e)
        {
            dto.setStatus(HttpStatus.NOT_FOUND);
            dto.setMessage("Couldn't find user that matches userId: " + userId);
        }

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceSpaceDTO>>(dto, dto.getStatus());
    }

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentVersionDTO>> getContentVersionsForUser(
        String userId, LocalDate createdDate, LocalDate startDate, LocalDate endDate
    ){
        ConfluenceResultsRootDTO<ConfluenceContentVersionDTO> dto = new ConfluenceResultsRootDTO<ConfluenceContentVersionDTO>();
        ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null);

        try
        {
            dto.setResults(new ArrayList<ConfluenceContentVersionDTO>());
            dto.setStatus(HttpStatus.OK);

            if (createdDate == null) 
            {
                if (startDate != null && endDate != null) 
                {
                    userEntity.getContentVersions().forEach((version) -> {
                        if (version.getWhen() != null) 
                        {
                            LocalDate versionCreationDate = DateConversion.stringToLocalDate(version.getWhen()); 
                            if (
                                versionCreationDate.isAfter(startDate) && versionCreationDate.isBefore(endDate)
                            ){
                                ConfluenceContentVersionDTO versionDTO = this.contentVersionMapper.entityToDTO(version);
                                dto.getResults().add(versionDTO);
                            }
                        }
                    });
                } 
                else if (startDate != null)
                {
                    userEntity.getContentVersions().forEach((version) -> {
                        if (version.getWhen() != null) 
                        {
                            LocalDate versionCreationDate = DateConversion.stringToLocalDate(version.getWhen()); 
                            if (versionCreationDate.isAfter(startDate))
                            {
                                ConfluenceContentVersionDTO versionDTO = this.contentVersionMapper.entityToDTO(version);
                                dto.getResults().add(versionDTO);
                            }
                        }
                    });
                }
                else if (endDate != null)
                {
                    userEntity.getContentVersions().forEach((version) -> {
                        if (version.getWhen() != null) 
                        {
                            LocalDate versionCreationDate = DateConversion.stringToLocalDate(version.getWhen()); 
                            if (versionCreationDate.isBefore(endDate))
                            {
                                ConfluenceContentVersionDTO versionDTO = this.contentVersionMapper.entityToDTO(version);
                                dto.getResults().add(versionDTO);
                            }
                        }
                    });
                }
                else 
                {
                    userEntity.getContentVersions().forEach((version) -> {
                        ConfluenceContentVersionDTO versionDTO = this.contentVersionMapper.entityToDTO(version);
                        dto.getResults().add(versionDTO);
                    });    
                }
            }
            else 
            {
                userEntity.getContentVersions().forEach((version) -> {
                    if (version.getWhen() != null) 
                    {                
                        LocalDate versionCreationDate = DateConversion.stringToLocalDate(version.getWhen());     
                        if (versionCreationDate.isEqual(createdDate))
                        {
                            ConfluenceContentVersionDTO versionDTO = this.contentVersionMapper.entityToDTO(version);
                            dto.getResults().add(versionDTO);
                        }   
                    }
                });
            }

            dto.setSize(dto.getResults().size());
        }
        catch(NullPointerException e)
        {
            dto.setMessage("Couldn't find user that matches userId: " + userId);
            dto.setStatus(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentVersionDTO>>(dto, dto.getStatus());
    }

    public ResponseEntity<ConfluenceResultRootDTO<ConfluenceUserStatisticsDTO>> getUserStatistics(
        String userId, LocalDate statisticsDate, LocalDate startDate, LocalDate endDate
    ){
        ConfluenceResultRootDTO<ConfluenceUserStatisticsDTO> dto = new ConfluenceResultRootDTO<ConfluenceUserStatisticsDTO>(); 
        ConfluenceUserEntity userEntity = this.confluenceUserRepository.findById(userId).orElse(null); 
        
        try
        { 
            dto.setStatus(HttpStatus.OK);
            dto.setResult(new ConfluenceUserStatisticsDTO());

            dto.getResult().setCompletedTasks(new ArrayList<ConfluenceTaskDTO>());
            dto.getResult().setCreatedTasks(new ArrayList<ConfluenceTaskDTO>());
            dto.getResult().setContentVersions(new ArrayList<ConfluenceContentVersionDTO>());
            dto.getResult().setNumberOfCompletedTasks(0);
            dto.getResult().setNumberOfCreatedTasks(0);
            dto.getResult().setNumberOfWrittenLines(0);

            dto.getResult().setUser(this.userMapper.entityToDTO(userEntity));

            if (statisticsDate == null) 
            {
                if (startDate != null && endDate != null) 
                {
                    userEntity.getCreatedTasks().forEach((task) -> {
                        if (task.createdAt() != null) 
                        {
                            LocalDate createdTaskDate = DateConversion.stringToLocalDate(task.createdAt()); 
                            if (startDate.isBefore(createdTaskDate) && createdTaskDate.isBefore(endDate))
                            {
                                dto.getResult().setNumberOfCreatedTasks(
                                    dto.getResult().getNumberOfCreatedTasks() + 1
                                );
                                dto.getResult().getCreatedTasks().add(
                                    this.taskMapper.entityToDTO(task) 
                                );
                            }    
                        }
                    });
    
                    userEntity.getCompletedTasks().forEach((task) -> {
                        if (task.completedAt() != null) 
                        {
                            LocalDate completedTaskDate = DateConversion.stringToLocalDate(task.completedAt()); 
                            if (startDate.isBefore(completedTaskDate) && completedTaskDate.isBefore(endDate))
                            {
                                dto.getResult().setNumberOfCompletedTasks(
                                    dto.getResult().getNumberOfCompletedTasks() + 1
                                );
                                dto.getResult().getCompletedTasks().add(
                                    this.taskMapper.entityToDTO(task) 
                                );
                            }    
                        }
                    });
    
                    userEntity.getContentVersions().forEach((version) -> {
                        if (version.getWhen() != null) 
                        {
                            LocalDate versionCreationDate = DateConversion.stringToLocalDate(version.getWhen()); 
                            if (startDate.isBefore(versionCreationDate) && versionCreationDate.isBefore(endDate))
                            {
                                dto.getResult().setNumberOfWrittenLines(
                                    dto.getResult().getNumberOfWrittenLines() + version.getAddedLines()
                                );
                                dto.getResult().getContentVersions().add(
                                    this.contentVersionMapper.entityToDTO(version) 
                                );
                            }
                        }
                    });
                }
                else if (startDate != null)
                {
                    userEntity.getCreatedTasks().forEach((task) -> {
                        if (task.createdAt() != null) 
                        {
                            LocalDate createdTaskDate = DateConversion.stringToLocalDate(task.createdAt()); 
                            if (createdTaskDate.isAfter(startDate))
                            {
                                dto.getResult().setNumberOfCreatedTasks(
                                    dto.getResult().getNumberOfCreatedTasks() + 1
                                );
                                dto.getResult().getCreatedTasks().add(
                                    this.taskMapper.entityToDTO(task) 
                                );
                            }    
                        }
                    });

                    userEntity.getCompletedTasks().forEach((task) -> {
                        if (task.completedAt() != null) 
                        {
                            LocalDate completedTaskDate = DateConversion.stringToLocalDate(task.completedAt()); 
                            if (completedTaskDate.isAfter(startDate))
                            {
                                dto.getResult().setNumberOfCompletedTasks(
                                    dto.getResult().getNumberOfCompletedTasks() + 1
                                );
                                dto.getResult().getCompletedTasks().add(
                                    this.taskMapper.entityToDTO(task) 
                                );
                            }    
                        }
                    });

                    userEntity.getContentVersions().forEach((version) -> {
                        if (version.getWhen() != null) 
                        {
                            LocalDate versionCreationDate = DateConversion.stringToLocalDate(version.getWhen()); 
                            if (versionCreationDate.isAfter(startDate))
                            {
                                dto.getResult().setNumberOfWrittenLines(
                                    dto.getResult().getNumberOfWrittenLines() + version.getAddedLines()
                                );
                                dto.getResult().getContentVersions().add(
                                    this.contentVersionMapper.entityToDTO(version) 
                                );
                            }
                        }
                    });
                }
                else if (endDate != null)
                {
                    userEntity.getCreatedTasks().forEach((task) -> {
                        if (task.createdAt() != null) 
                        {
                            LocalDate createdTaskDate = DateConversion.stringToLocalDate(task.createdAt()); 
                            if (createdTaskDate.isBefore(endDate))
                            {
                                dto.getResult().setNumberOfCreatedTasks(
                                    dto.getResult().getNumberOfCreatedTasks() + 1
                                );
                                dto.getResult().getCreatedTasks().add(
                                    this.taskMapper.entityToDTO(task) 
                                );
                            }    
                        }
                    });

                    userEntity.getCompletedTasks().forEach((task) -> {
                        if (task.completedAt() != null) 
                        {
                            LocalDate completedTaskDate = DateConversion.stringToLocalDate(task.completedAt()); 
                            if (completedTaskDate.isBefore(endDate))
                            {
                                dto.getResult().setNumberOfCompletedTasks(
                                    dto.getResult().getNumberOfCompletedTasks() + 1
                                );
                                dto.getResult().getCompletedTasks().add(
                                    this.taskMapper.entityToDTO(task) 
                                );
                            }    
                        }
                    });

                    userEntity.getContentVersions().forEach((version) -> {
                        if (version.getWhen() != null) 
                        {
                            LocalDate versionCreationDate = DateConversion.stringToLocalDate(version.getWhen()); 
                            if (versionCreationDate.isBefore(endDate))
                            {
                                dto.getResult().setNumberOfWrittenLines(
                                    dto.getResult().getNumberOfWrittenLines() + version.getAddedLines()
                                );
                                dto.getResult().getContentVersions().add(
                                    this.contentVersionMapper.entityToDTO(version) 
                                );
                            }
                        }
                    });
                }
                else 
                {
                    userEntity.getContentVersions().forEach((version) -> {
                        dto.getResult().setNumberOfWrittenLines(
                            dto.getResult().getNumberOfWrittenLines() + version.getAddedLines()
                        );
                        dto.getResult().getContentVersions().add(
                            this.contentVersionMapper.entityToDTO(version) 
                        );
                    });
                    
                    userEntity.getCompletedTasks().forEach((task) -> {
                        dto.getResult().setNumberOfCompletedTasks(
                            dto.getResult().getNumberOfCompletedTasks() + 1
                        );
                        dto.getResult().getCompletedTasks().add(
                            this.taskMapper.entityToDTO(task) 
                        );
                    });
    
                    userEntity.getCreatedTasks().forEach((task) -> {
                        dto.getResult().setNumberOfCreatedTasks(
                            dto.getResult().getNumberOfCreatedTasks() + 1
                        );
                        dto.getResult().getCreatedTasks().add(
                            this.taskMapper.entityToDTO(task)
                        );
                    });
                }
            }
            else 
            {
                userEntity.getContentVersions().forEach((version) -> {
                    if (version.getWhen() != null) 
                    {
                        LocalDate versionCreationDate = DateConversion.stringToLocalDate(version.getWhen()); 
                        if (statisticsDate.isEqual(versionCreationDate)) 
                        {
                            dto.getResult().setNumberOfWrittenLines(
                                dto.getResult().getNumberOfWrittenLines() + version.getAddedLines()
                            );
                            dto.getResult().getContentVersions().add(
                                this.contentVersionMapper.entityToDTO(version) 
                            ); 
                        }
                    }
                });

                userEntity.getCreatedTasks().forEach((task) -> {
                    if (task.createdAt() != null) 
                    {
                        LocalDate createdTaskDate = DateConversion.stringToLocalDate(task.createdAt()); 
                        if (statisticsDate.isEqual(createdTaskDate)) 
                        {
                            dto.getResult().setNumberOfCreatedTasks(
                                dto.getResult().getNumberOfCreatedTasks() + 1
                            );   
                            dto.getResult().getCreatedTasks().add(
                                this.taskMapper.entityToDTO(task)
                            );
                        }    
                    }
                });

                userEntity.getCompletedTasks().forEach((task) -> {
                    if (task.completedAt() != null) 
                    {
                        LocalDate completedTaskDate = DateConversion.stringToLocalDate(task.completedAt());
                        if (statisticsDate.isEqual(completedTaskDate)) 
                        {
                            dto.getResult().setNumberOfCompletedTasks(
                                dto.getResult().getNumberOfCompletedTasks() + 1
                            );
                            dto.getResult().getCompletedTasks().add(
                                this.taskMapper.entityToDTO(task)
                            );
                        }    
                    } 
                });
            }
        }
        catch(NullPointerException e)
        {
            dto.setResult(null);
            dto.setMessage("Couldn't find user that matches userId: " + userId);
            dto.setStatus(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ConfluenceResultRootDTO<ConfluenceUserStatisticsDTO>>(dto, dto.getStatus()); 
    }
    

    /* -------------------- TASK METHODS  -------------------- */

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceTaskDTO>> saveTasks()  
    {
        ConfluenceResultsRootDTO<ConfluenceTaskDTO> dto = new ConfluenceResultsRootDTO<ConfluenceTaskDTO>();
        ConfluenceRootResponse<ConfluenceTaskResponse> response = this.confluenceClient.fetchAllTasks();
        
        ArrayList<ConfluenceTaskEntity> tasks = new ArrayList<ConfluenceTaskEntity>();

        dto.setResults(new ArrayList<ConfluenceTaskDTO>());
        
        response.getResults().forEach((result) -> {
            ConfluenceTaskEntity taskEntity = this.confluenceTaskRepository.findById(result.getId()).orElse(new ConfluenceTaskEntity()); 
            this.taskMapper.convertResponseToEntityWithoutConsideringRelations(taskEntity, result);

            taskEntity.setAssignedTo(result.assignedTo() != null ? this.confluenceUserRepository.findById(result.assignedTo()).orElse(null) : null);
            taskEntity.setCompletedBy(result.completedBy() != null ? this.confluenceUserRepository.findById(result.completedBy()).orElse(null) : null); 
            taskEntity.setCreatedBy( result.createdBy() != null ? this.confluenceUserRepository.findById(result.createdBy()).orElse(null) : null);
            taskEntity.setPage(result.getPageId() != null ? this.confluenceContentRepository.findById(result.getPageId()).orElse(null) : null);
            taskEntity.setSpace(result.getSpaceId() != null ? this.confluenceSpaceRepository.findById(result.getSpaceId()).orElse(null) : null);
            
            tasks.add(taskEntity); 

            ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(taskEntity);
            dto.getResults().add(taskDTO);
        });

        this.confluenceTaskRepository.saveAll(tasks);

        dto.setSize(tasks.size());
        dto.setStatus(HttpStatus.CREATED);

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceTaskDTO>>(dto, dto.getStatus());
    }

    public ConfluenceResultsRootDTO<ConfluenceTaskDTO> getAllTasks(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<ConfluenceTaskEntity> taskPage = this.confluenceTaskRepository.findAll(pageable);

        ConfluenceResultsRootDTO<ConfluenceTaskDTO> dto = new ConfluenceResultsRootDTO<ConfluenceTaskDTO>();
        ArrayList<ConfluenceTaskEntity> taskEntities = new ArrayList<ConfluenceTaskEntity>(taskPage.getContent());
    
        dto.setPageNumber(pageNumber);
        dto.setResults(new ArrayList<ConfluenceTaskDTO>());
        
        taskEntities.forEach((taskEntity) -> {
            ConfluenceTaskDTO taskDTO = this.taskMapper.entityToDTO(taskEntity);
            dto.getResults().add(taskDTO);
        });

        dto.setSize(dto.getResults().size());
        dto.setStatus(HttpStatus.OK);

        return dto;
    }

    public ResponseEntity<ConfluenceResultRootDTO<ConfluenceTaskDTO>> getTaskById(String taskId)
    {
        ConfluenceTaskEntity taskEntity = this.confluenceTaskRepository.findById(taskId).orElse(null);
        ConfluenceResultRootDTO<ConfluenceTaskDTO> dto = new ConfluenceResultRootDTO<ConfluenceTaskDTO>(); 
        if (taskEntity == null) 
        {
            dto.setStatus(HttpStatus.NOT_FOUND);
            dto.setMessage("Couldn't find task with id: " + taskId);
        }
        else 
        {
            dto.setStatus(HttpStatus.OK);
            dto.setResult(this.taskMapper.entityToDTO(taskEntity));
        }
        return new ResponseEntity<ConfluenceResultRootDTO<ConfluenceTaskDTO>>(dto, dto.getStatus());
    }


    /* -------------------- SPACE METHODS  -------------------- */
    
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceSpaceDTO>> saveSpaces()
    {
        ConfluenceResultsRootDTO<ConfluenceSpaceDTO> dto = new ConfluenceResultsRootDTO<ConfluenceSpaceDTO>();
        ConfluenceRootResponse<ConfluenceSpaceResponse> response = this.confluenceClient.fetchAllSpaces();
        
        ArrayList<ConfluenceSpaceEntity> spaces = new ArrayList<ConfluenceSpaceEntity>();
        ArrayList<ConfluenceContentEntity> contents = new ArrayList<ConfluenceContentEntity>(); 
        
        dto.setResults(new ArrayList<ConfluenceSpaceDTO>());

        response.getResults().stream().forEach((result) -> {
            ConfluenceSpaceEntity spaceEntity = this.confluenceSpaceRepository.findById(result.getId()).orElse(new ConfluenceSpaceEntity()); 
            this.spaceMapper.convertResponseToEntityWithoutConsideringRelations(spaceEntity, result);

            spaceEntity.setAuthor(result.getAuthorId() != null ? this.confluenceUserRepository.findById(result.getAuthorId()).orElse(null) : null);
            spaceEntity.setHomepage(result.getHomepageId() != null ? this.confluenceContentRepository.findById(result.getHomepageId()).orElse(null) : null);

            spaceEntity.setPages(new ArrayList<ConfluenceContentEntity>());

            ConfluencePageRootResponse<ConfluenceContentResponse> contentResponseForSpace = this.confluenceClient.fetchContentsForASpace(result.getKey()); 

            contentResponseForSpace.getPage().getResults().stream().forEach((contentResult) -> {
                ConfluenceContentEntity contentEntity = this.confluenceContentRepository.findById(contentResult.getId()).orElse(new ConfluenceContentEntity()); 
                this.contentMapper.convertResponseToEntityWithoutConsideringRelations(contentEntity, contentResult);
                contents.add(contentEntity); 
                spaceEntity.getPages().add(contentEntity); 
            });  

            spaces.add(spaceEntity);
            ConfluenceSpaceDTO spaceDTO = this.spaceMapper.entityToDTO(spaceEntity);
            dto.getResults().add(spaceDTO);
        });
        
        this.confluenceContentRepository.saveAll(contents); 
        this.confluenceSpaceRepository.saveAll(spaces);

        dto.setSize(dto.getResults().size());
        dto.setStatus(HttpStatus.CREATED);

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceSpaceDTO>>(dto, dto.getStatus());
    }

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceSpaceDTO>> getAllSpaces(int pageNumber, int size)
    {
        Pageable pageable = PageRequest.of(pageNumber, size);
        Page<ConfluenceSpaceEntity> spacePage = this.confluenceSpaceRepository.findAll(pageable);

        ConfluenceResultsRootDTO<ConfluenceSpaceDTO> dto = new ConfluenceResultsRootDTO<ConfluenceSpaceDTO>();
        ArrayList<ConfluenceSpaceEntity> spaceEntities = new ArrayList<ConfluenceSpaceEntity>(spacePage.getContent());
        
        dto.setPageNumber(pageNumber);
        dto.setResults(new ArrayList<ConfluenceSpaceDTO>());
        
        spaceEntities.forEach((spaceEntity) -> {
            ConfluenceSpaceDTO spaceDTO = this.spaceMapper.entityToDTO(spaceEntity);
            dto.getResults().add(spaceDTO);
        });
        
        dto.setSize(dto.getResults().size());
        dto.setStatus(HttpStatus.OK);

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceSpaceDTO>>(dto, dto.getStatus());
    }

    public ResponseEntity<ConfluenceResultRootDTO<ConfluenceSpaceDTO>> getSpaceById(String spaceId)
    {
        ConfluenceSpaceEntity spaceEntity = this.confluenceSpaceRepository.findById(spaceId).orElse(null);
        ConfluenceResultRootDTO<ConfluenceSpaceDTO> dto = new ConfluenceResultRootDTO<ConfluenceSpaceDTO>();
        if (spaceEntity == null)
        {
            dto.setStatus(HttpStatus.NOT_FOUND);
            dto.setMessage("Couldn't find space with id: " + spaceId);
        }
        else 
        {
            dto.setStatus(HttpStatus.OK);
            dto.setResult(this.spaceMapper.entityToDTO(spaceEntity));
        }
        return new ResponseEntity<ConfluenceResultRootDTO<ConfluenceSpaceDTO>>(dto, dto.getStatus());
    }

    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentDTO>> getSpaceContents(String spaceId)
    {
        ConfluenceSpaceEntity spaceEntity = this.confluenceSpaceRepository.findById(spaceId).orElse(null);
        ConfluenceResultsRootDTO<ConfluenceContentDTO> dto = new ConfluenceResultsRootDTO<ConfluenceContentDTO>();

        try
        {
            dto.setStatus(HttpStatus.OK);
            dto.setSize(spaceEntity.getPages().size());
            dto.setResults(new ArrayList<ConfluenceContentDTO>());
            
            spaceEntity.getPages().forEach((pageEntity) -> {
                ConfluenceContentDTO contentDTO = this.contentMapper.entityToDTO(pageEntity);
                dto.getResults().add(contentDTO);
            });
        }
        catch(NullPointerException e)
        {
            dto.setStatus(HttpStatus.NOT_FOUND);
            dto.setMessage("Couldn't find space with id :" + spaceId);
        }

        return new ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentDTO>>(dto, dto.getStatus());
    }
}