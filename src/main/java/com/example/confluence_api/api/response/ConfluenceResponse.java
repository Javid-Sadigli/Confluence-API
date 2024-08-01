package com.example.confluence_api.api.response;

import java.util.ArrayList;

public class ConfluenceResponse
{
    public ArrayList<ResultResponse> results;
    public int start;
    public int limit;
    public int size;
    public LinksResponse _links;
}