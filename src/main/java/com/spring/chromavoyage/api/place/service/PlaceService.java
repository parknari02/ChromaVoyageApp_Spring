package com.chromavoyage.api.place.service;

import com.chromavoyage.api.place.entity.PlaceEntity;
import com.chromavoyage.api.place.repository.PlaceRepository;
import com.chromavoyage.api.place.dto.PlaceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;


    public void save(PlaceDTO placeDTO) {
        PlaceEntity placeEntity = PlaceEntity.toPlaceEntity(placeDTO);
        placeRepository.save(placeEntity);
    }

    public List<PlaceEntity> findAll() {
        return placeRepository.findAll();
    }

    public List<PlaceEntity> findPlaceByGroupIdAndColoringLocationId(int groupId, int coloringLocationId) {
        return placeRepository.findByGroupIdAndColoringLocationId(groupId, coloringLocationId);

    }

    public void deleteByPlaceName(String placeName) {
        placeRepository.deleteByPlaceName(placeName);

    }
}
