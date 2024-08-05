package com.example.confluence_api.service;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.confluence_api.client.ConfluenceClient;
import com.example.confluence_api.client.model.ConfluenceResponse;
import com.example.confluence_api.client.model.ContentResponse;
import com.example.confluence_api.dto.ConfluenceRootDTO;
import com.example.confluence_api.dto.ContentDTO;
import com.example.confluence_api.entity.ContentEntity;
import com.example.confluence_api.mapper.ContentMapper;
import com.example.confluence_api.mapper.LinksMapper;
import com.example.confluence_api.repository.ConfluenceRepository;

@Service
public class ConfluenceService 
{
    private final ContentMapper contentMapper; 
    private final LinksMapper linksMapper; 
    private final ConfluenceClient confluenceClient; 
    private final ConfluenceRepository confluenceContentRepository; 

    public ConfluenceService(
        ContentMapper contentMapper, 
        LinksMapper linksMapper,
        ConfluenceClient confluenceClient, 
        ConfluenceRepository confluenceContentRepository
    ){
        this.confluenceClient = confluenceClient; 
        this.contentMapper = contentMapper; 
        this.linksMapper = linksMapper; 
        this.confluenceContentRepository = confluenceContentRepository; 
    }

    public ConfluenceRootDTO<ContentDTO> saveContents()
    {
        ConfluenceRootDTO<ContentDTO> dto = new ConfluenceRootDTO<ContentDTO>();
        ConfluenceResponse<ContentResponse> response = this.confluenceClient.fetchAllContents();
        
        ArrayList<ContentEntity> contents = new ArrayList<ContentEntity>();

        dto.limit = response.limit; 
        dto.size = response.size; 
        dto.start = response.start; 
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

    public ConfluenceRootDTO<ContentDTO> getAllContents(int start, int limit)
    {
        Pageable pageble = PageRequest.of(start, limit); 
        Page<ContentEntity> contentPage = this.confluenceContentRepository.findAll(pageble);

        ConfluenceRootDTO<ContentDTO> dto = new ConfluenceRootDTO<ContentDTO>();
        ArrayList<ContentEntity> contentEntities = new ArrayList<ContentEntity>(contentPage.getContent()); 
        
        dto.size = contentEntities.size();
        dto.start = start; 
        dto.limit = limit; 
        dto.results = new ArrayList<ContentDTO>();
        
        contentEntities.forEach((contentEntity) -> {
            ContentDTO contentDTO = this.contentMapper.entityToDTO(contentEntity);
            dto.results.add(contentDTO); 
        });

        return dto;
    }

}
