package com.example.confluence_api.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "confluence_users")
public class ConfluenceUserEntity 
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
    public ConfluenceProfilePictureEntity profilePicture;

    @OneToMany
    @JoinColumn(name = "created_by")
    public List<ConfluenceTaskEntity> createdTasks;

    @OneToMany
    @JoinColumn(name = "completed_by")
    public List<ConfluenceTaskEntity> completedTasks;

    @OneToMany
    @JoinColumn(name = "assigned_to")
    public List<ConfluenceTaskEntity> assignedTasks;

    @OneToMany
    @JoinColumn(name = "author_id")
    public List<ConfluenceSpaceEntity> spaces; 

    
    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        ConfluenceUserEntity userEntity = (ConfluenceUserEntity) o;

        return accountId != null ? accountId.equals(userEntity.accountId) : userEntity.accountId == null;
    }

    @Override public int hashCode() 
    {
        return this.accountId != null ? this.accountId.hashCode() : 0;
    }
}
