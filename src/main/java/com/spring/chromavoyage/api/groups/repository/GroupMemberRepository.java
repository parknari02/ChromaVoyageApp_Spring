package com.spring.chromavoyage.api.groups.repository;

import com.spring.chromavoyage.api.groups.entity.Group;
import com.spring.chromavoyage.api.groups.entity.GroupMember;
import com.spring.chromavoyage.api.groups.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
    boolean existsByGroupAndUser(Group group, User user);

    void deleteByGroupAndUser(Group group, User user);

    List<GroupMember> findByGroup(Group group);
    // GroupMember 엔티티에 대한 추가적인 쿼리 메서드가 필요하다면 여기에 작성할 수 있습니다.
    // ...
}
