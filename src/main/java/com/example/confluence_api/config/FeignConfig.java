package com.example.confluence_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.auth.BasicAuthRequestInterceptor;

@Configuration
public class FeignConfig 
{
    @Value("${feign.auth.username}")
    private String username;

    @Value("${feign.auth.token}")
    private String token;

    @Bean 
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() 
    {
        return new BasicAuthRequestInterceptor(username, token);
    }
}
