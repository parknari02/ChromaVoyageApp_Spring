package com.spring.chromavoyage.api.place.repository;
import com.spring.chromavoyage.api.place.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<PlaceEntity, Integer> {


    List<PlaceEntity> findByGroupIdAndColoringLocationId(Long groupId, Long coloringLocationId);

    @Transactional
    void deleteByPlaceName(String placeName);
}
