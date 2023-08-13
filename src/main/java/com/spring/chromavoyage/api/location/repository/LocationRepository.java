package com.spring.chromavoyage.api.location.repository;
import com.spring.chromavoyage.api.location.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    public Location findLocationByLocationId(Long location_id);
    public Location findLocationByLocationName(String location_name);
}
