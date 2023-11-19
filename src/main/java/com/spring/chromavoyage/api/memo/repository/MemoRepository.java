package com.spring.chromavoyage.api.memo.repository;

import com.spring.chromavoyage.api.memo.entitiy.MemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<MemoEntity, Integer> {

    List<MemoEntity> findByGroupIdAndColoringLocationId(Long groupId, Long coloringLocationId);
    // 추가적인 메소드가 필요한 경우 여기에 정의
}
