package com.example.confluence_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.confluence_api.dto.ConfluenceRootDTO;
import com.example.confluence_api.dto.ContentDTO;
import com.example.confluence_api.dto.GroupDTO;
import com.example.confluence_api.dto.SpaceDTO;
import com.example.confluence_api.dto.TaskDTO;
import com.example.confluence_api.dto.UserDTO;
import com.example.confluence_api.service.ConfluenceService;

@RestController
@RequestMapping("/confluence/api")
public class ConfluenceController 
{
    @Autowired private final ConfluenceService confluenceService; 

    public ConfluenceController(ConfluenceService confluenceService)
    {
        this.confluenceService = confluenceService; 
    }


    /* -------------------- CONTENT METHODS  -------------------- */

    @PostMapping("/content/save") 
    public ConfluenceRootDTO<ContentDTO> saveContents(@RequestBody Object body)
    {
        return this.confluenceService.saveContents(); 
    }

    @GetMapping("/content/get/all")
    public ConfluenceRootDTO<ContentDTO> getAllContents(
        @RequestParam(defaultValue = "0") int pageNumber, 
        @RequestParam(defaultValue = "100") int size
    ){
        return this.confluenceService.getAllContents(pageNumber, size);  
    }

    @GetMapping("/content/get/{id}")
    public ContentDTO getContentById(@PathVariable String id)
    {
        return this.confluenceService.getContentById(id);
    }


    /* -------------------- GROUP METHODS  -------------------- */

    @PostMapping("/group/save")
    public ConfluenceRootDTO<GroupDTO> saveGroups(@RequestBody Object body)
    {
        return this.confluenceService.saveGroups(); 
    }

    @GetMapping("/group/get/all")
    public ConfluenceRootDTO<GroupDTO> getAllGroups(
        @RequestParam(defaultValue = "0") int pageNumber, 
        @RequestParam(defaultValue = "100") int size
    ){
        return this.confluenceService.getAllGroups(pageNumber, size);
    }

    @GetMapping("/group/get/{id}")
    public GroupDTO getGroupById(@PathVariable String id)
    {
        return this.confluenceService.getGroupById(id);
    }

    @PostMapping("/group/{id}/members/save")
    public ConfluenceRootDTO<UserDTO> saveGroupMembers(@PathVariable String id)
    {
        return this.confluenceService.saveGroupMembers(id);
    }

    @GetMapping("/group/{id}/members/get")
    public ConfluenceRootDTO<UserDTO> getGroupMembers(@PathVariable String id)
    {
        return this.confluenceService.getGroupMembers(id); 
    }


    /* -------------------- USER METHODS  -------------------- */

    @PostMapping("/user/save")
    public ConfluenceRootDTO<UserDTO> saveUsers(@RequestBody Object body)
    {
        return this.confluenceService.saveUsers();  
    }

    @GetMapping("/user/get/all")
    public ConfluenceRootDTO<UserDTO> getAllUsers(
        @RequestParam(defaultValue = "0") int pageNumber, 
        @RequestParam(defaultValue = "100") int size
    ){
        return this.confluenceService.getAllUsers(pageNumber, size);
    }

    @GetMapping("/user/get/{id}")
    public UserDTO getUserById(@PathVariable String id)
    {
        return this.confluenceService.getUserById(id);
    }

    @GetMapping("/user/{id}/tasks/completed/get")
    public ConfluenceRootDTO<TaskDTO> getCompletedTasksForAUser(@PathVariable String id)
    {
        return this.confluenceService.getUserCompletedTasks(id);
    }

    @GetMapping("/user/{id}/tasks/assigned/get")
    public ConfluenceRootDTO<TaskDTO> getAssignedTasksForAUser(@PathVariable String id)
    {
        return this.confluenceService.getUserAssignedTasks(id); 
    }

    @GetMapping("/user/{id}/tasks/created/get")
    public ConfluenceRootDTO<TaskDTO> getCreatedTasksForAUser(@PathVariable String id)
    {
        return this.confluenceService.getUserCreatedTasks(id);
    }


    /* -------------------- TASK METHODS  -------------------- */
    
    @PostMapping("/task/save")
    public ConfluenceRootDTO<TaskDTO> saveTasks(@RequestBody Object body)
    {
        return this.confluenceService.saveTasks(); 
    }

    @GetMapping("/task/get/all")
    public ConfluenceRootDTO<TaskDTO> getAllTasks(
        @RequestParam(defaultValue = "0") int pageNumber, 
        @RequestParam(defaultValue = "100") int size
    ){
        return this.confluenceService.getAllTasks(pageNumber, size);
    }

    @GetMapping("/task/get/{id}")
    public TaskDTO getTaskById(@PathVariable String id)
    {
        return this.confluenceService.getTaskById(id); 
    }
    

    /* -------------------- SPACE METHODS  -------------------- */

    @PostMapping("/space/save")
    public ConfluenceRootDTO<SpaceDTO> saveSpaces(@RequestBody Object body)
    {
        return this.confluenceService.saveSpaces(); 
    }

    @GetMapping("/space/get/all")
    public ConfluenceRootDTO<SpaceDTO> getAllSpaces(
        @RequestParam(defaultValue = "0") int pageNumber, 
        @RequestParam(defaultValue = "100") int size
    ){
        return this.confluenceService.getAllSpaces(pageNumber, size);
    }

    @GetMapping("/space/get/{id}")
    public SpaceDTO getSpaceById(@PathVariable String id)
    {
        return this.confluenceService.getSpaceById(id); 
    }
}