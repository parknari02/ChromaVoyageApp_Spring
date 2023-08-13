package com.spring.chromavoyage.api.images.repository;

import com.spring.chromavoyage.api.images.domain.ImageDto;
//import org.springframework.data.jpa.repository.JpaRepository;
import com.spring.chromavoyage.api.images.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
//    public List<ImageEntity> findAllImageEntitiesByColoringLocationIdAndGroupIdAndLocationId(Long coloringLocationId, Long groupId, Long locationId);
    public List<ImageEntity> findAllByColoringLocationId(Long coloringLocationId);
    public void deleteByImageId(Long imageId);
}
