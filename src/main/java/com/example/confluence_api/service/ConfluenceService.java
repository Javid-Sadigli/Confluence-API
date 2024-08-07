package com.example.confluence_api.service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.confluence_api.client.ConfluenceClient;
import com.example.confluence_api.client.model.ConfluenceRootResponse;
import com.example.confluence_api.client.model.ContentResponse;
import com.example.confluence_api.client.model.GroupResponse;
import com.example.confluence_api.client.model.UserResponse;
import com.example.confluence_api.dto.ConfluenceRootDTO;
import com.example.confluence_api.dto.ContentDTO;
import com.example.confluence_api.dto.GroupDTO;
import com.example.confluence_api.dto.UserDTO;
import com.example.confluence_api.entity.ContentEntity;
import com.example.confluence_api.entity.GroupEntity;
import com.example.confluence_api.entity.UserEntity;
import com.example.confluence_api.mapper.ContentMapper;
import com.example.confluence_api.mapper.GroupMapper;
import com.example.confluence_api.mapper.UserMapper;
import com.example.confluence_api.repository.ConfluenceContentRepository;
import com.example.confluence_api.repository.ConfluenceGroupRepository;
import com.example.confluence_api.repository.ConfluenceUserRepository;

@Service
public class ConfluenceService 
{
    private final ContentMapper contentMapper; 
    private final GroupMapper groupMapper; 
    private final UserMapper userMapper;
    private final ConfluenceClient confluenceClient; 
    private final ConfluenceContentRepository confluenceContentRepository; 
    private final ConfluenceGroupRepository confluenceGroupRepository; 
    private final ConfluenceUserRepository confluenceUserRepository; 

    public ConfluenceService(
        ContentMapper contentMapper, 
        GroupMapper groupMapper, 
        UserMapper userMapper,
        ConfluenceClient confluenceClient, 
        ConfluenceContentRepository confluenceContentRepository, 
        ConfluenceGroupRepository confluenceGroupRepository, 
        ConfluenceUserRepository confluenceUserRepository
    ){
        this.confluenceClient = confluenceClient; 
        this.contentMapper = contentMapper; 
        this.groupMapper = groupMapper; 
        this.userMapper = userMapper;
        this.confluenceContentRepository = confluenceContentRepository; 
        this.confluenceGroupRepository = confluenceGroupRepository;
        this.confluenceUserRepository = confluenceUserRepository;
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
            UserEntity userEntity = this.userMapper.responseToEntity(member); 
            this.confluenceUserRepository.save(userEntity);
            UserDTO userDTO = this.userMapper.entityToDTO(userEntity);
            dto.results.add(userDTO);
            groupEntity.members.add(userEntity); 
        });

        this.confluenceGroupRepository.save(groupEntity); 
        return dto; 
    }
}
