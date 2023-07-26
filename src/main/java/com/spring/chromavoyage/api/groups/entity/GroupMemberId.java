package com.spring.chromavoyage.api.groups.entity;

import java.io.Serializable;

public class GroupMemberId implements Serializable {
    private Long group;
    private Long user;

    // 기본 생성자
    public GroupMemberId() {
    }

    // 생성자
    public GroupMemberId(Long group, Long user) {
        this.group = group;
        this.user = user;
    }

    // Getter와 Setter
    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }
}
