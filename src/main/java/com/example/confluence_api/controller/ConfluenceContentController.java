package com.example.confluence_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.confluence_api.dto.ConfluenceRootDTO;
import com.example.confluence_api.service.ConfluenceContentService;

@RestController
@RequestMapping("/confluence/api/content")
public class ConfluenceContentController 
{
    @Autowired private final ConfluenceContentService confluenceContentService; 

    public ConfluenceContentController(ConfluenceContentService confluenceContentService)
    {
        this.confluenceContentService = confluenceContentService; 
    }

    @PostMapping("/save") 
    public ConfluenceRootDTO saveContents(@RequestBody Object body)
    {
        return this.confluenceContentService.saveContents(); 
    }
    

}