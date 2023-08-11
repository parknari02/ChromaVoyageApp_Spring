package com.spring.chromavoyage.api.location.repository;
import com.spring.chromavoyage.api.location.entity.UserColoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface UserColoringRepository extends JpaRepository<UserColoring, Long> {
    public List<UserColoring> findUserColoringByUserId(Long userId);
}
