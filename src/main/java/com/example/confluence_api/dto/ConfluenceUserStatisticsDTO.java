package com.example.confluence_api.dto;

import java.util.ArrayList;

public class ConfluenceUserStatisticsDTO 
{
    private ConfluenceUserDTO user; 
    private int numberOfWrittenLines;
    private int numberOfCompletedTasks; 
    private int numberOfCreatedTasks; 

    private ArrayList<ConfluenceContentVersionDTO> contentVersions; 
    private ArrayList<ConfluenceTaskDTO> completedTasks; 
    private ArrayList<ConfluenceTaskDTO> createdTasks; 
    

    public ConfluenceUserDTO getUser() {
        return this.user;
    }

    public void setUser(ConfluenceUserDTO user) {
        this.user = user;
    }

    public int getNumberOfWrittenLines() {
        return this.numberOfWrittenLines;
    }

    public void setNumberOfWrittenLines(int numberOfWrittenLines) {
        this.numberOfWrittenLines = numberOfWrittenLines;
    }

    public int getNumberOfCompletedTasks() {
        return this.numberOfCompletedTasks;
    }

    public void setNumberOfCompletedTasks(int numberOfCompletedTasks) {
        this.numberOfCompletedTasks = numberOfCompletedTasks;
    }

    public int getNumberOfCreatedTasks() {
        return this.numberOfCreatedTasks;
    }

    public void setNumberOfCreatedTasks(int numberOfCreatedTasks) {
        this.numberOfCreatedTasks = numberOfCreatedTasks;
    }


    public ArrayList<ConfluenceContentVersionDTO> getContentVersions() {
        return this.contentVersions;
    }

    public void setContentVersions(ArrayList<ConfluenceContentVersionDTO> contentVersions) {
        this.contentVersions = contentVersions;
    }

    public ArrayList<ConfluenceTaskDTO> getCompletedTasks() {
        return this.completedTasks;
    }

    public void setCompletedTasks(ArrayList<ConfluenceTaskDTO> completedTasks) {
        this.completedTasks = completedTasks;
    }

    public ArrayList<ConfluenceTaskDTO> getCreatedTasks() {
        return this.createdTasks;
    }

    public void setCreatedTasks(ArrayList<ConfluenceTaskDTO> createdTasks) {
        this.createdTasks = createdTasks;
    }

    
}
