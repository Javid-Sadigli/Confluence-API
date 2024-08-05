package com.example.confluence_api.client.model;

import java.util.ArrayList;

public class ConfluenceResponse
{
    public ArrayList<ContentResponse> results;
    public int start;
    public int limit;
    public int size;
    public LinksResponse _links;
}