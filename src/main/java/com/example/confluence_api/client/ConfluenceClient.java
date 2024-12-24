package com.example.confluence_api.client;

import com.example.confluence_api.client.model.ConfluenceContentResponse;
import com.example.confluence_api.client.model.ConfluenceContentVersionResponse;
import com.example.confluence_api.client.model.ConfluenceGroupResponse;
import com.example.confluence_api.client.model.ConfluencePageRootResponse;
import com.example.confluence_api.client.model.ConfluenceRootResponse;
import com.example.confluence_api.client.model.ConfluenceSpaceResponse;
import com.example.confluence_api.client.model.ConfluenceTaskResponse;
import com.example.confluence_api.client.model.ConfluenceUserResponse;
import com.example.confluence_api.config.ConfluenceFeignConfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "confluence-client", url = "${feign.client.url}", configuration = ConfluenceFeignConfig.class)
public interface ConfluenceClient
{
    @GetMapping("/rest/api/content")
    public ConfluenceRootResponse<ConfluenceContentResponse> fetchAllContents();

    @GetMapping("/rest/api/content/{id}/version")
    public ConfluenceRootResponse<ConfluenceContentVersionResponse> fetchVersionsForAContent(
        @PathVariable String id
    ); 

    @GetMapping("/rest/api/content/{id}?expand=body.storage")
    public ConfluenceContentResponse fetchExpandedContentById(
        @PathVariable String id, 
        @RequestParam(value = "version", required = true) int versionNumber
    ); 

    @GetMapping("/rest/api/group") 
    public ConfluenceRootResponse<ConfluenceGroupResponse> fetchAllGroups(); 

    @GetMapping("/rest/api/group/{id}/membersByGroupId") 
    public ConfluenceRootResponse<ConfluenceUserResponse> fetchUsersForAGroup(
        @PathVariable String id
    );

    @GetMapping("/rest/api/space/{spaceKey}/content")
    public ConfluencePageRootResponse<ConfluenceContentResponse> fetchContentsForASpace(
        @PathVariable String spaceKey
    );


    @GetMapping("/api/v2/tasks")
    public ConfluenceRootResponse<ConfluenceTaskResponse> fetchAllTasks();

    @GetMapping("/api/v2/spaces")
    public ConfluenceRootResponse<ConfluenceSpaceResponse> fetchAllSpaces();
}