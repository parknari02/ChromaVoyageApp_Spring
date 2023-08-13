package com.spring.chromavoyage.api.groups.repository;

import com.spring.chromavoyage.api.groups.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    public boolean existsByGroupIdAndUserId(Long groupId, Long userId);

    public void deleteByGroupIdAndUserId(Long groupId, Long userId);

    public List<GroupMember> findGroupMemberByGroupId(Long groupId);

    public List<GroupMember> findGroupMemberByUserId(Long userId);

}
