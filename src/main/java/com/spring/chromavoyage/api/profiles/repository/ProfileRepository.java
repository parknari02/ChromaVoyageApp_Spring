package com.spring.chromavoyage.api.profiles.repository;

import com.spring.chromavoyage.api.profiles.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    public ProfileEntity findByUserId(Long userId);
    public void deleteByUserId(Long userId);

}
