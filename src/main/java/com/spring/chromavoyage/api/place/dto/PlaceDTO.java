package com.spring.chromavoyage.api.place.dto;


import com.spring.chromavoyage.api.place.entity.PlaceEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PlaceDTO {
    private Long placeListId;
    private Long coloringLocationId;
    private Long groupId;
    private String locationId;
    private String placeName;
    private String address;
    private double latitude;
    private double longitude;

    public static PlaceDTO toPlaceDTO(PlaceEntity placeEntity) {
        PlaceDTO placeDTO = new PlaceDTO();
        placeDTO.setPlaceListId(placeEntity.getPlaceListId());
        placeDTO.setColoringLocationId(placeEntity.getColoringLocationId());
        placeDTO.setGroupId(placeEntity.getGroupId());
        placeDTO.setLocationId(placeEntity.getLocationId());
        placeDTO.setPlaceName(placeEntity.getPlaceName());
        placeDTO.setAddress(placeEntity.getAddress());
        placeDTO.setLatitude(placeEntity.getLatitude());
        placeDTO.setLongitude(placeEntity.getLongitude());
        return placeDTO;
    }
}
