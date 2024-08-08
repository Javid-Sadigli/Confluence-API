package com.example.confluence_api.client.model;

public class ConfluenceUserResponse 
{
    public String type;
    public String accountId;
    public String accountType;
    public String email;
    public String publicName;
    public ConfluenceProfilePictureResponse profilePicture;
    public String displayName;
    public boolean isExternalCollaborator;
    public boolean isGuest;
}