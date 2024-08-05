package com.example.confluence_api.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.confluence_api.client.ConfluenceClient;
import com.example.confluence_api.client.model.ConfluenceResponse;
import com.example.confluence_api.client.model.ContentResponse;
import com.example.confluence_api.dto.ConfluenceRootDTO;
import com.example.confluence_api.dto.ContentDTO;
import com.example.confluence_api.entity.ContentEntity;
import com.example.confluence_api.mapper.ContentMapper;
import com.example.confluence_api.mapper.LinksMapper;
import com.example.confluence_api.repository.ConfluenceContentRepository;

@Service
public class ConfluenceService 
{
    private final ContentMapper contentMapper; 
    private final LinksMapper linksMapper; 
    private final ConfluenceClient confluenceClient; 
    private final ConfluenceContentRepository confluenceContentRepository; 

    public ConfluenceService(
        ContentMapper contentMapper, 
        LinksMapper linksMapper,
        ConfluenceClient confluenceClient, 
        ConfluenceContentRepository confluenceContentRepository
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

    public ConfluenceRootDTO<ContentDTO> getAllContents()
    {
        ConfluenceRootDTO<ContentDTO> dto = new ConfluenceRootDTO<ContentDTO>();
        ArrayList<ContentEntity> contentEntities = (ArrayList<ContentEntity>)this.confluenceContentRepository.findAll(); 
        
        dto.size = contentEntities.size();
        dto.start = 0; 
        dto.limit = 100; 
        dto.results = new ArrayList<ContentDTO>();
        
        contentEntities.forEach((contentEntity) -> {
            ContentDTO contentDTO = this.contentMapper.entityToDTO(contentEntity);
            dto.results.add(contentDTO); 
        });

        return dto;
    }

}
