package com.spring.chromavoyage.api.place.service;
import com.spring.chromavoyage.api.place.dto.PlaceDTO;
import com.spring.chromavoyage.api.place.entity.PlaceEntity;
import com.spring.chromavoyage.api.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {
    @Autowired
    private PlaceRepository placeRepository;

    public void save(PlaceDTO placeDTO) {
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setPlaceName(placeDTO.getPlaceName());
        placeEntity.setColoringLocationId(placeDTO.getColoringLocationId());
        placeEntity.setGroupId(placeDTO.getGroupId());
        placeEntity.setLocationId(placeDTO.getLocationId());
        placeEntity.setPlaceName(placeDTO.getPlaceName());
        placeEntity.setAddress(placeDTO.getAddress());
        placeEntity.setLatitude(placeDTO.getLatitude());
        placeEntity.setLongitude(placeDTO.getLongitude());
        placeEntity.setPlaceDate(placeDTO.getPlaceDate());
        placeEntity.setCreatedDate(LocalDateTime.now());
        placeEntity.setStartTime(placeDTO.getStartTime());
        placeEntity.setEndTime(placeDTO.getEndTime());
        placeRepository.save(placeEntity);
    }

    public List<PlaceEntity> findAll() {
        return placeRepository.findAll();
    }

    public List<PlaceEntity> findPlaceByGroupIdAndColoringLocationId(Long groupId, Long coloringLocationId) {
        return placeRepository.findByGroupIdAndColoringLocationId(groupId, coloringLocationId);

    }



    public void deleteByPlaceName(Long coloringLocationId, String placeName){
        List<PlaceEntity> placelist = placeRepository.findPlaceEntityByColoringLocationId(coloringLocationId);
        for(PlaceEntity place:placelist){
            if(place.getPlaceName().equals(placeName)){
                placeRepository.delete(place);
            }
        }

    }
}
