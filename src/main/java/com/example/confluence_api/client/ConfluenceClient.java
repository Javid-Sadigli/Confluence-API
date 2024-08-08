package com.example.confluence_api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.confluence_api.client.model.ConfluenceRootResponse;
import com.example.confluence_api.client.model.ConfluenceContentResponse;
import com.example.confluence_api.client.model.ConfluenceGroupResponse;
import com.example.confluence_api.client.model.ConfluenceSpaceResponse;
import com.example.confluence_api.client.model.ConfluenceTaskResponse;
import com.example.confluence_api.client.model.ConfluenceUserResponse;
import com.example.confluence_api.config.ConfluenceFeignConfig;

@FeignClient(name = "confluence-client", url = "${feign.client.url}", configuration = ConfluenceFeignConfig.class)
public interface ConfluenceClient 
{
    @GetMapping("/rest/api/content") public ConfluenceRootResponse<ConfluenceContentResponse> fetchAllContents(); 
    @GetMapping("/rest/api/group") public ConfluenceRootResponse<ConfluenceGroupResponse> fetchAllGroups(); 
    @GetMapping("/rest/api/group/{id}/membersByGroupId") public ConfluenceRootResponse<ConfluenceUserResponse> fetchUsersForAGroup(@PathVariable String id);

    @GetMapping("/api/v2/tasks") public ConfluenceRootResponse<ConfluenceTaskResponse> fetchAllTasks(); 
    @GetMapping("/api/v2/spaces") public ConfluenceRootResponse<ConfluenceSpaceResponse> fetchAllSpaces();
}