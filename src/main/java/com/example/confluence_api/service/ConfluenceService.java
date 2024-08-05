package com.example.confluence_api.service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.confluence_api.client.ConfluenceClient;
import com.example.confluence_api.client.model.ConfluenceResponse;
import com.example.confluence_api.client.model.ContentResponse;
import com.example.confluence_api.client.model.GroupResponse;
import com.example.confluence_api.dto.ConfluenceRootDTO;
import com.example.confluence_api.dto.ContentDTO;
import com.example.confluence_api.dto.GroupDTO;
import com.example.confluence_api.entity.ContentEntity;
import com.example.confluence_api.entity.GroupEntity;
import com.example.confluence_api.mapper.ContentMapper;
import com.example.confluence_api.mapper.GroupMapper;
import com.example.confluence_api.mapper.LinksMapper;
import com.example.confluence_api.repository.ConfluenceContentRepository;
import com.example.confluence_api.repository.ConfluenceGroupRepository;

@Service
public class ConfluenceService 
{
    private final ContentMapper contentMapper; 
    private final LinksMapper linksMapper; 
    private final GroupMapper groupMapper; 
    private final ConfluenceClient confluenceClient; 
    private final ConfluenceContentRepository confluenceContentRepository; 
    private final ConfluenceGroupRepository confluenceGroupRepository; 

    public ConfluenceService(
        ContentMapper contentMapper, 
        LinksMapper linksMapper,
        GroupMapper groupMapper, 
        ConfluenceClient confluenceClient, 
        ConfluenceContentRepository confluenceContentRepository, 
        ConfluenceGroupRepository confluenceGroupRepository
    ){
        this.confluenceClient = confluenceClient; 
        this.contentMapper = contentMapper; 
        this.linksMapper = linksMapper; 
        this.groupMapper = groupMapper; 
        this.confluenceContentRepository = confluenceContentRepository; 
        this.confluenceGroupRepository = confluenceGroupRepository;
    }


    /* -------------------- CONTENT METHODS  -------------------- */

    public ConfluenceRootDTO<ContentDTO> saveContents()
    {
        ConfluenceRootDTO<ContentDTO> dto = new ConfluenceRootDTO<ContentDTO>();
        ConfluenceResponse<ContentResponse> response = this.confluenceClient.fetchAllContents();
        
        ArrayList<ContentEntity> contents = new ArrayList<ContentEntity>();

        dto.size = response.size; 
        dto._links = this.linksMapper.entityToDTO(
            this.linksMapper.responseToEntity(response._links)
        ); 
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
        ConfluenceResponse<GroupResponse> response = this.confluenceClient.fetchAllGroups();
        
        ArrayList<GroupEntity> groups = new ArrayList<GroupEntity>();

        dto.size = response.size; 
        dto._links = this.linksMapper.entityToDTO(
            this.linksMapper.responseToEntity(response._links)
        ); 
        dto.results = new ArrayList<GroupDTO>();

        response.results.forEach((result) -> {
            GroupEntity groupEntity = this.groupMapper.responseToEntity(result); 
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
}
