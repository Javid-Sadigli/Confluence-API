package com.example.confluence_api.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.confluence_api.api.ConfluenceClient;
import com.example.confluence_api.api.response.ConfluenceResponse;
import com.example.confluence_api.dto.ConfluenceRootDTO;
import com.example.confluence_api.dto.ContentDTO;
import com.example.confluence_api.entity.ContentEntity;
import com.example.confluence_api.mapper.ContentMapper;
import com.example.confluence_api.mapper.LinksMapper;
import com.example.confluence_api.repository.ConfluenceContentRepository;

@Service
public class ConfluenceContentService 
{
    private final ContentMapper contentMapper; 
    private final LinksMapper linksMapper; 
    private final ConfluenceClient confluenceClient; 
    private final ConfluenceContentRepository confluenceContentRepository; 

    @Autowired
    public ConfluenceContentService(
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

    public ConfluenceRootDTO saveContents()
    {
        ConfluenceRootDTO dto = new ConfluenceRootDTO();
        ConfluenceResponse response = this.confluenceClient.fetchAllContents();
        
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

}
