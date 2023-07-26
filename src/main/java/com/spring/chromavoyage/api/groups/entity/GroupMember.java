package com.spring.chromavoyage.api.groups.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;
@Entity
@Table(name = "GROUPMEMBER")
@IdClass(GroupMemberId.class)
public class GroupMember implements Serializable {
    @Id
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // 생성자
    public GroupMember(Group group, User user) {
        this.group = group;
        this.user = user;
    }

    public GroupMember() {

    }

    // Getter와 Setter
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
