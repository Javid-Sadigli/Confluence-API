package com.example.confluence_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.confluence_api.dto.ConfluenceRootDTO;
import com.example.confluence_api.dto.ContentDTO;
import com.example.confluence_api.service.ConfluenceService;

@RestController
@RequestMapping("/confluence/api")
public class ConfluenceController 
{
    @Autowired private final ConfluenceService confluenceContentService; 

    public ConfluenceController(ConfluenceService confluenceContentService)
    {
        this.confluenceContentService = confluenceContentService; 
    }

    @PostMapping("/content/save") 
    public ConfluenceRootDTO<ContentDTO> saveContents(@RequestBody Object body)
    {
        return this.confluenceContentService.saveContents(); 
    }

    @GetMapping("/content/getAll")
    public ConfluenceRootDTO<ContentDTO> getAllContents()
    {
        return this.confluenceContentService.getAllContents();  
    }
}