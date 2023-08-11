package com.spring.chromavoyage.api.groups.domain;

import com.spring.chromavoyage.api.groups.service.UserInfo;

import java.util.List;

public class CreateGroupRequest {
    private Long userId;
    private String group_name;
    private List<UserInfo> invited_emails;

    public Long getUserId() {
        return userId;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public List<UserInfo> getInvited_emails() {
        return invited_emails;
    }

    public void setInvited_users(List<UserInfo> invited_users) {
        this.invited_emails = invited_users;
    }
}
