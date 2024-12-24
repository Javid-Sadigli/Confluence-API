package com.example.confluence_api.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.confluence_api.dto.ConfluenceContentDTO;
import com.example.confluence_api.dto.ConfluenceContentVersionDTO;
import com.example.confluence_api.dto.ConfluenceGroupDTO;
import com.example.confluence_api.dto.ConfluenceResultRootDTO;
import com.example.confluence_api.dto.ConfluenceResultsRootDTO;
import com.example.confluence_api.dto.ConfluenceSpaceDTO;
import com.example.confluence_api.dto.ConfluenceTaskDTO;
import com.example.confluence_api.dto.ConfluenceUserDTO;
import com.example.confluence_api.dto.ConfluenceUserStatisticsDTO;
import com.example.confluence_api.service.ConfluenceService;

@RestController
@RequestMapping("/api/v1/confluence")
public class ConfluenceController 
{
    @Autowired private final ConfluenceService confluenceService; 

    public ConfluenceController(ConfluenceService confluenceService)
    {
        this.confluenceService = confluenceService; 
    }


    /* -------------------- CONTENT METHODS  -------------------- */

    @PostMapping("/content/save") 
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentDTO>> saveContents(@RequestBody Object body)
    {
        return this.confluenceService.saveContents(); 
    }

    @GetMapping("/content/get/all")
    public ConfluenceResultsRootDTO<ConfluenceContentDTO> getAllContents(
        @RequestParam(defaultValue = "0") int pageNumber, 
        @RequestParam(defaultValue = "10") int size
    ){
        return this.confluenceService.getAllContents(pageNumber, size);  
    }

    @GetMapping("/content/get/{id}")
    public ResponseEntity<ConfluenceResultRootDTO<ConfluenceContentDTO>> getContentById(@PathVariable String id)
    {
        return this.confluenceService.getContentById(id);
    }

    @GetMapping("/content/{id}/versions/get")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentVersionDTO>> getContentVersions(@PathVariable String id)
    {
        return this.confluenceService.getContentVersions(id); 
    }


    /* -------------------- CONTENT VERSION METHODS  -------------------- */

    @PostMapping("/content/version/save")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentVersionDTO>> saveContentVersions(@RequestBody Object body)
    {
        return this.confluenceService.saveContentVersions(); 
    }


    /* -------------------- GROUP METHODS  -------------------- */

    @PostMapping("/group/save")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceGroupDTO>> saveGroups(@RequestBody Object body)
    {
        return this.confluenceService.saveGroups(); 
    }

    @GetMapping("/group/get/all")
    public ConfluenceResultsRootDTO<ConfluenceGroupDTO> getAllGroups(
        @RequestParam(defaultValue = "0") int pageNumber, 
        @RequestParam(defaultValue = "10") int size
    ){
        return this.confluenceService.getAllGroups(pageNumber, size);
    }

    @GetMapping("/group/get/{id}")
    public ResponseEntity<ConfluenceResultRootDTO<ConfluenceGroupDTO>> getGroupById(@PathVariable String id)
    {
        return this.confluenceService.getGroupById(id);
    }

    @PostMapping("/group/{id}/members/save")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceUserDTO>> saveGroupMembers(@PathVariable String id)
    {
        return this.confluenceService.saveGroupMembers(id);
    }

    @GetMapping("/group/{id}/members/get")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceUserDTO>> getGroupMembers(@PathVariable String id)
    {
        return this.confluenceService.getGroupMembers(id); 
    }


    /* -------------------- USER METHODS  -------------------- */

    @PostMapping("/user/save")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceUserDTO>> saveUsers(@RequestBody Object body)
    {
        return this.confluenceService.saveUsers();  
    }

    @GetMapping("/user/get/all")
    public ConfluenceResultsRootDTO<ConfluenceUserDTO> getAllUsers(
        @RequestParam(defaultValue = "0") int pageNumber, 
        @RequestParam(defaultValue = "10") int size
    ){
        return this.confluenceService.getAllUsers(pageNumber, size);
    }

    @GetMapping("/user/get/{id}")
    public ResponseEntity<ConfluenceResultRootDTO<ConfluenceUserDTO>> getUserById(@PathVariable String id)
    {
        return this.confluenceService.getUserById(id);
    }

    @GetMapping("/user/{id}/tasks/completed/get")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceTaskDTO>> getCompletedTasksForAUser(
        @PathVariable String id, 
        @RequestParam(required = false, value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate completedDate, 
        @RequestParam(required = false, value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate starDate, 
        @RequestParam(required = false, value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate 
    ){
        return this.confluenceService.getUserCompletedTasks(id, completedDate, starDate, endDate);
    }
    
    @GetMapping("/user/{id}/tasks/assigned/get")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceTaskDTO>> getAssignedTasksForAUser(@PathVariable String id)
    {
        return this.confluenceService.getUserAssignedTasks(id); 
    }

    @GetMapping("/user/{id}/tasks/created/get")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceTaskDTO>> getCreatedTasksForAUser(
        @PathVariable String id, 
        @RequestParam(required = false, value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdDate,
        @RequestParam(required = false, value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate starDate, 
        @RequestParam(required = false, value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate 
    ){
        return this.confluenceService.getUserCreatedTasks(id, createdDate, starDate, endDate);
    }

    @GetMapping("/user/{id}/spaces/get")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceSpaceDTO>> getSpacesForUser(@PathVariable String id)
    {
        return this.confluenceService.getUserSpaces(id);
    }

    @GetMapping("/user/{id}/content/versions/get")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentVersionDTO>> getContentVersionsForAUser(
        @PathVariable String id, 
        @RequestParam(required = false, value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate createdDate,
        @RequestParam(required = false, value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate starDate, 
        @RequestParam(required = false, value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate 
    ){
        return this.confluenceService.getContentVersionsForUser(id, createdDate, starDate, endDate);
    }
    
    @GetMapping("/user/{id}/statistics/get")
    public ResponseEntity<ConfluenceResultRootDTO<ConfluenceUserStatisticsDTO>> getUserStatistics(
        @PathVariable String id, 
        @RequestParam(required = false, value = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate statisticsDate,
        @RequestParam(required = false, value = "startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate starDate, 
        @RequestParam(required = false, value = "endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate 
    ){
        return this.confluenceService.getUserStatistics(id, statisticsDate, starDate, endDate); 
    }


    /* -------------------- TASK METHODS  -------------------- */
    
    @PostMapping("/task/save")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceTaskDTO>> saveTasks(@RequestBody Object body)
    {
        return this.confluenceService.saveTasks(); 
    }

    @GetMapping("/task/get/all")
    public ConfluenceResultsRootDTO<ConfluenceTaskDTO> getAllTasks(
        @RequestParam(defaultValue = "0") int pageNumber, 
        @RequestParam(defaultValue = "10") int size
    ){
        return this.confluenceService.getAllTasks(pageNumber, size);
    }

    @GetMapping("/task/get/{id}")
    public ResponseEntity<ConfluenceResultRootDTO<ConfluenceTaskDTO>> getTaskById(@PathVariable String id)
    {
        return this.confluenceService.getTaskById(id); 
    }
    

    /* -------------------- SPACE METHODS  -------------------- */

    @PostMapping("/space/save")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceSpaceDTO>> saveSpaces(@RequestBody Object body)
    {
        return this.confluenceService.saveSpaces(); 
    }

    @GetMapping("/space/get/all")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceSpaceDTO>> getAllSpaces(
        @RequestParam(defaultValue = "0") int pageNumber, 
        @RequestParam(defaultValue = "10") int size
    ){
        return this.confluenceService.getAllSpaces(pageNumber, size);
    }

    @GetMapping("/space/get/{id}")
    public ResponseEntity<ConfluenceResultRootDTO<ConfluenceSpaceDTO>> getSpaceById(@PathVariable String id)
    {
        return this.confluenceService.getSpaceById(id); 
    }

    @GetMapping("/space/{id}/contents/get")
    public ResponseEntity<ConfluenceResultsRootDTO<ConfluenceContentDTO>> getContentsInASpace(@PathVariable String id)
    {
        return this.confluenceService.getSpaceContents(id); 
    }
}