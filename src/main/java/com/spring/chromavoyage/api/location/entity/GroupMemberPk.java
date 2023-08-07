package com.spring.chromavoyage.api.location.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
public class GroupMemberPk implements Serializable {
    private Long groupId;
    private Long userId;

}