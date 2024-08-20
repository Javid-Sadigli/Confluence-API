package com.example.confluence_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfluenceUserDTO 
{
    private String accountId;
    private String type;    
    private String accountType;
    private String email;
    private String publicName;
    private ConfluenceProfilePictureDTO profilePicture;
    private String displayName;
    private boolean isExternalCollaborator;
    private boolean isGuest;    


    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
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

    public ConfluenceProfilePictureDTO getProfilePicture() {
        return this.profilePicture;
    }

    public void setProfilePicture(ConfluenceProfilePictureDTO profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @JsonProperty("isExternalCollabrator")
    public boolean isExternalCollaborator() {
        return this.isExternalCollaborator;
    }

    public void setIsExternalCollaborator(boolean isExternalCollaborator) {
        this.isExternalCollaborator = isExternalCollaborator;
    }

    @JsonProperty("isGuest")
    public boolean isGuest() {
        return this.isGuest;
    }

    public void setIsGuest(boolean isGuest) {
        this.isGuest = isGuest;
    }


    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        ConfluenceUserDTO userDTO = (ConfluenceUserDTO) o;

        return accountId != null ? accountId.equals(userDTO.accountId) : userDTO.accountId == null;
    }

    @Override public int hashCode() 
    {
        return this.accountId != null ? this.accountId.hashCode() : 0;
    }
}
