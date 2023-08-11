package com.spring.chromavoyage.api.location.repository;

import com.spring.chromavoyage.api.location.entity.GroupMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    public List<GroupMember> findByGroupId(Long groupId);


}
