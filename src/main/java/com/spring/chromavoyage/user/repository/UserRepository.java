package com.spring.chromavoyage.user.repository;

import com.spring.chromavoyage.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    User findByEmail(String email);

    User findUserByUserId(Long userId);
}
