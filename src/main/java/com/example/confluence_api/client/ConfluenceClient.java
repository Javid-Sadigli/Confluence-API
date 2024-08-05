package com.example.confluence_api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.confluence_api.client.model.ConfluenceResponse;
import com.example.confluence_api.config.FeignConfig;

@FeignClient(name = "confluence-client", url = "https://javidsadigli.atlassian.net/wiki", configuration = FeignConfig.class)
public interface ConfluenceClient 
{
    @GetMapping("/rest/api/content") public ConfluenceResponse fetchAllContents(); 
}