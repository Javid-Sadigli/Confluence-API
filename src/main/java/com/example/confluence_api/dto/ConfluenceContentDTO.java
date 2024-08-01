package com.example.confluence_api.dto;

import java.util.ArrayList;

import com.example.confluence_api.entity.ContentEntity;
import com.example.confluence_api.entity.LinksEntity;

public class ConfluenceContentDTO 
{
    public ArrayList<ContentEntity> results;
    public int start;
    public int limit;
    public int size;
    public LinksEntity _links;
}
