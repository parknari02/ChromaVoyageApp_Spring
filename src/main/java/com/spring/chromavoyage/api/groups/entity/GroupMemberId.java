package com.spring.chromavoyage.api.groups.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class GroupMemberId implements Serializable {
    private Long groupId;
    private Long userId;

}
