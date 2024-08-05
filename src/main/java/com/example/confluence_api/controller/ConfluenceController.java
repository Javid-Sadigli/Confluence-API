package com.example.confluence_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.confluence_api.dto.ConfluenceRootDTO;
import com.example.confluence_api.service.ConfluenceService;

@RestController
@RequestMapping("/confluence/api/content")
public class ConfluenceController 
{
    @Autowired private final ConfluenceService confluenceContentService; 

    public ConfluenceController(ConfluenceService confluenceContentService)
    {
        this.confluenceContentService = confluenceContentService; 
    }

    @PostMapping("/save") 
    public ConfluenceRootDTO saveContents(@RequestBody Object body)
    {
        return this.confluenceContentService.saveContents(); 
    }
}