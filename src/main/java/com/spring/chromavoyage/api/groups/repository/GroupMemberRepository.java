package com.spring.chromavoyage.api.groups.repository;

import com.spring.chromavoyage.api.groups.entity.Group;
import com.spring.chromavoyage.api.groups.entity.GroupMember;
import com.spring.chromavoyage.api.groups.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    boolean existsByGroupIdAndUserId(Long groupId, Long userId);

    void deleteByGroupIdAndUserId(Long groupId, Long userId);

    List<GroupMember> findByGroupId(Long groupId);


    // GroupMember 엔티티에 대한 추가적인 쿼리 메서드가 필요하다면 여기에 작성할 수 있습니다.
    // ...
}
