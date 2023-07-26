package com.spring.chromavoyage.api.groups.repository;

import com.spring.chromavoyage.api.groups.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 조회를 위한 추가 메서드 등 필요한 경우 작성
}
