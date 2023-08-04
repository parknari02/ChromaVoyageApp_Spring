package com.spring.chromavoyage.api.groups.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "GROUPMEMBER")
@IdClass(GroupMemberId.class)
public class GroupMember implements Serializable {
    @Id
    @Column(name = "group_id")
    private Long groupId;

    @Id
    @Column(name = "user_id")
    private Long userId;
}
