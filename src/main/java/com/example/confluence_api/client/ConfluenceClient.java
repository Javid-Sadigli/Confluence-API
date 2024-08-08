package com.example.confluence_api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.confluence_api.client.model.ConfluenceRootResponse;
import com.example.confluence_api.client.model.ContentResponse;
import com.example.confluence_api.client.model.GroupResponse;
import com.example.confluence_api.client.model.SpaceResponse;
import com.example.confluence_api.client.model.TaskResponse;
import com.example.confluence_api.client.model.UserResponse;
import com.example.confluence_api.config.FeignConfig;

@FeignClient(name = "confluence-client", url = "${feign.client.url}", configuration = FeignConfig.class)
public interface ConfluenceClient 
{
    @GetMapping("/rest/api/content") public ConfluenceRootResponse<ContentResponse> fetchAllContents(); 
    @GetMapping("/rest/api/group") public ConfluenceRootResponse<GroupResponse> fetchAllGroups(); 
    @GetMapping("/rest/api/group/{id}/membersByGroupId") public ConfluenceRootResponse<UserResponse> fetchUsersForAGroup(@PathVariable String id);

    @GetMapping("/api/v2/tasks") public ConfluenceRootResponse<TaskResponse> fetchAllTasks(); 
    @GetMapping("/api/v2/spaces") public ConfluenceRootResponse<SpaceResponse> fetchAllSpaces();
}