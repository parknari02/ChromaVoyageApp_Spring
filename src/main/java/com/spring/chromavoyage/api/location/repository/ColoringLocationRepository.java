package com.spring.chromavoyage.api.location.repository;
import com.spring.chromavoyage.api.location.entity.ColoringLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColoringLocationRepository extends JpaRepository <ColoringLocation, Long>{
    public List<ColoringLocation> findColoringPlaceByGroupId(Long group_id);

}

