package com.example.confluence_api.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.confluence_api.api.response.ConfluenceResponse;

@FeignClient(name = "confluence-client", url = "https://javidsadigli.atlassian.net/wiki/rest/api")
public interface ConfluenceClient 
{
    @GetMapping("/content") public ConfluenceResponse fetchAllContents(); 
}