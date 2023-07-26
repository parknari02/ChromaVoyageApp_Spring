package com.spring.chromavoyage.api.groups.controller;

public class GroupInvitationResponse {
    private Long userId;
    private String email;
    private String userName;
    private String profileImagePath;

    public GroupInvitationResponse(Long userId, String email, String userName, String profileImagePath) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.profileImagePath = profileImagePath;
    }

    public GroupInvitationResponse() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImagePath() {
        return profileImagePath;
    }

    public void setProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}
