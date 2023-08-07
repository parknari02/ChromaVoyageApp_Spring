package com.spring.chromavoyage.api.groups.repository;

import com.spring.chromavoyage.api.groups.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsByGroupName(String groupName);

    Group findGroupByGroupId(Long groupId);
}
