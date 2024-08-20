package com.example.confluence_api.client.model;

public class ConfluenceUserResponse 
{
    private String type;
    private String accountId;
    private String accountType;
    private String email;
    private String publicName;
    private ConfluenceProfilePictureResponse profilePicture;
    private String displayName;
    private boolean isExternalCollaborator;
    private boolean isGuest;


    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPublicName() {
        return this.publicName;
    }

    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

    public ConfluenceProfilePictureResponse getProfilePicture() {
        return this.profilePicture;
    }

    public void setProfilePicture(ConfluenceProfilePictureResponse profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isExternalCollaborator() {
        return this.isExternalCollaborator;
    }

    public void setIsExternalCollaborator(boolean isExternalCollaborator) {
        this.isExternalCollaborator = isExternalCollaborator;
    }

    public boolean isGuest() {
        return this.isGuest;
    }

    public void setIsGuest(boolean isGuest) {
        this.isGuest = isGuest;
    }

}