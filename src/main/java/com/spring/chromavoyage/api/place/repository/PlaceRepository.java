package com.spring.chromavoyage.api.place.repository;
import com.spring.chromavoyage.api.place.entity.PlaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;


public interface PlaceRepository extends JpaRepository<PlaceEntity, Integer> {


    List<PlaceEntity> findByGroupIdAndColoringLocationId(int groupId, int coloringLocationId);

    @Transactional
    void deleteByPlaceName(String placeName);
}
