package com.example.confluence_api.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "confluence_users")
public class ConfluenceUserEntity 
{
    @Id
    private String accountId;

    private String type;    
    private String accountType;
    private String email;
    private String publicName;
    private String displayName;
    private boolean isExternalCollaborator;
    private boolean isGuest;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_picture_id")
    private ConfluenceProfilePictureEntity profilePicture;

    @OneToMany
    @JoinColumn(name = "created_by")
    private List<ConfluenceTaskEntity> createdTasks;

    @OneToMany
    @JoinColumn(name = "completed_by")
    private List<ConfluenceTaskEntity> completedTasks;

    @OneToMany
    @JoinColumn(name = "assigned_to")
    private List<ConfluenceTaskEntity> assignedTasks;

    @OneToMany
    @JoinColumn(name = "author_id")
    private List<ConfluenceSpaceEntity> spaces; 

    @OneToMany
    @JoinColumn(name = "by")
    private List<ConfluenceContentVersionEntity> contentVersions; 



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

    public ConfluenceProfilePictureEntity getProfilePicture() {
        return this.profilePicture;
    }

    public void setProfilePicture(ConfluenceProfilePictureEntity profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<ConfluenceTaskEntity> getCreatedTasks() {
        return this.createdTasks;
    }

    public void setCreatedTasks(List<ConfluenceTaskEntity> createdTasks) {
        this.createdTasks = createdTasks;
    }

    public List<ConfluenceTaskEntity> getCompletedTasks() {
        return this.completedTasks;
    }

    public void setCompletedTasks(List<ConfluenceTaskEntity> completedTasks) {
        this.completedTasks = completedTasks;
    }

    public List<ConfluenceTaskEntity> getAssignedTasks() {
        return this.assignedTasks;
    }

    public void setAssignedTasks(List<ConfluenceTaskEntity> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    public List<ConfluenceSpaceEntity> getSpaces() {
        return this.spaces;
    }

    public void setSpaces(List<ConfluenceSpaceEntity> spaces) {
        this.spaces = spaces;
    }

    public List<ConfluenceContentVersionEntity> getContentVersions() {
        return this.contentVersions;
    }

    public void setContentVersions(List<ConfluenceContentVersionEntity> contentVersions) {
        this.contentVersions = contentVersions;
    }


    
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