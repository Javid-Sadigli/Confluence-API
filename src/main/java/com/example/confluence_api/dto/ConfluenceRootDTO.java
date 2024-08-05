package com.example.confluence_api.dto;

import java.util.ArrayList;

public class ConfluenceRootDTO <T> 
{
    public ArrayList<T> results;
    public int pageNumber;
    public int size;
    public LinksDTO _links;
}
