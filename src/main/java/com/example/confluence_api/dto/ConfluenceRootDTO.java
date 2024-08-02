package com.example.confluence_api.dto;

import java.util.ArrayList;

public class ConfluenceRootDTO 
{
    public ArrayList<ContentDTO> results;
    public int start;
    public int limit;
    public int size;
    public LinksDTO _links;
}
