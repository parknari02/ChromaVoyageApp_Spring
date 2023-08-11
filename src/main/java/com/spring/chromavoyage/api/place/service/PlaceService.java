package com.spring.chromavoyage.api.place.service;
import com.spring.chromavoyage.api.place.dto.PlaceDTO;
import com.spring.chromavoyage.api.place.entity.PlaceEntity;
import com.spring.chromavoyage.api.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

    public void save(PlaceDTO placeDTO) {
        PlaceEntity placeEntity = PlaceEntity.toPlaceEntity(placeDTO);
        placeRepository.save(placeEntity);
    }

    public List<PlaceEntity> findAll() {
        return placeRepository.findAll();
    }

    public List<PlaceEntity> findPlaceByGroupIdAndColoringLocationId(Long groupId, Long coloringLocationId) {
        return placeRepository.findByGroupIdAndColoringLocationId(groupId, coloringLocationId);

    }

    public void deleteByPlaceName(String placeName) {
        placeRepository.deleteByPlaceName(placeName);

    }
}
