package com.spring.chromavoyage.api.groups.domain;

import java.util.List;

public class CreateGroupRequest {
    private String group_name;
    private List<String> invited_emails;

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public List<String> getInvited_emails() {
        return invited_emails;
    }

    public void setInvited_users(List<String> invited_users) {
        this.invited_emails = invited_users;
    }
}
