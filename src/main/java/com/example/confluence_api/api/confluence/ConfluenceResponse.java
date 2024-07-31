package com.example.confluence_api.api.confluence;

import java.util.ArrayList;

public class ConfluenceResponse
{
    public ArrayList<Result> results;
    public int start;
    public int limit;
    public int size;
    public Links _links;
}