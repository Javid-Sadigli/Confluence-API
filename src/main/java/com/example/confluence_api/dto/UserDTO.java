package com.example.confluence_api.dto;

public class UserDTO 
{
    public String accountId;
    public String type;    
    public String accountType;
    public String email;
    public String publicName;
    public ProfilePictureDTO profilePicture;
    public String displayName;
    public boolean isExternalCollaborator;
    public boolean isGuest;    

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        return accountId != null ? accountId.equals(userDTO.accountId) : userDTO.accountId == null;
    }

    @Override public int hashCode() 
    {
        return this.accountId != null ? this.accountId.hashCode() : 0;
    }
}
