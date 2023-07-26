package com.spring.chromavoyage.api.groups.controller;

import java.util.List;

public class CreateGroupRequest {
    private String group_name;
    private List<InvitedUser> invited_users;

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public List<InvitedUser> getInvited_users() {
        return invited_users;
    }

    public void setInvited_users(List<InvitedUser> invited_users) {
        this.invited_users = invited_users;
    }
}
