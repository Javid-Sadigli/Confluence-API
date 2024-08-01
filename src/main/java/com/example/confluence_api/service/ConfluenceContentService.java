package com.example.confluence_api.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.confluence_api.api.ConfluenceClient;
import com.example.confluence_api.api.response.ConfluenceResponse;
import com.example.confluence_api.dto.ConfluenceContentDTO;
import com.example.confluence_api.entity.ContentEntity;
import com.example.confluence_api.mapper.ContentMapper;
import com.example.confluence_api.mapper.LinksMapper;
import com.example.confluence_api.repository.ConfluenceContentRepository;

@Service
public class ConfluenceContentService 
{
    @Autowired private final ContentMapper contentMapper; 
    @Autowired private final LinksMapper linksMapper; 
    @Autowired private final ConfluenceClient confluenceClient; 
    @Autowired private final ConfluenceContentRepository confluenceContentRepository; 

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

    public ConfluenceContentDTO saveContents()
    {
        ConfluenceContentDTO dto = new ConfluenceContentDTO();
        ConfluenceResponse response = this.confluenceClient.fetchAllContents(); 

        dto.limit = response.limit; 
        dto.size = response.size; 
        dto.start = response.start; 
        dto._links = this.linksMapper.responseToEntity(response._links); 
        dto.results = new ArrayList<ContentEntity>();

        response.results.forEach((result) -> {
            dto.results.add(this.contentMapper.responseToEntity(result)); 
        });

        dto.results = (ArrayList<ContentEntity>)this.confluenceContentRepository.saveAll(dto.results); 

        return dto; 
    }

}
