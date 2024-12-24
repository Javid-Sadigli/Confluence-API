package com.example.confluence_api.client.model;

public class ConfluenceContentVersionResponse 
{
    private ConfluenceUserResponse by;   
    private String when;
    private String friendlyWhen;
    private String message;
    private int number;
    private boolean minorEdit;
    private String ncsStepVersion;
    private String ncsStepVersionSource;
    private String confRev;
    private boolean contentTypeModified;


    public ConfluenceUserResponse getBy() {
        return this.by;
    }

    public void setBy(ConfluenceUserResponse by) {
        this.by = by;
    }

    public String getWhen() {
        return this.when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

    public String getFriendlyWhen() {
        return this.friendlyWhen;
    }

    public void setFriendlyWhen(String friendlyWhen) {
        this.friendlyWhen = friendlyWhen;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isMinorEdit() {
        return this.minorEdit;
    }

    public void setMinorEdit(boolean minorEdit) {
        this.minorEdit = minorEdit;
    }

    public String getNcsStepVersion() {
        return this.ncsStepVersion;
    }

    public void setNcsStepVersion(String ncsStepVersion) {
        this.ncsStepVersion = ncsStepVersion;
    }

    public String getNcsStepVersionSource() {
        return this.ncsStepVersionSource;
    }

    public void setNcsStepVersionSource(String ncsStepVersionSource) {
        this.ncsStepVersionSource = ncsStepVersionSource;
    }

    public String getConfRev() {
        return this.confRev;
    }

    public void setConfRev(String confRev) {
        this.confRev = confRev;
    }

    public boolean isContentTypeModified() {
        return this.contentTypeModified;
    }

    public void setContentTypeModified(boolean contentTypeModified) {
        this.contentTypeModified = contentTypeModified;
    }

}
