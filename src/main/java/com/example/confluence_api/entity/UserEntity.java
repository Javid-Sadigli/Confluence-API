package com.example.confluence_api.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity 
{
    @Id
    public String accountId;

    public String type;    
    public String accountType;
    public String email;
    public String publicName;
    public String displayName;
    public boolean isExternalCollaborator;
    public boolean isGuest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_picture_id")
    public ProfilePictureEntity profilePicture;
}
