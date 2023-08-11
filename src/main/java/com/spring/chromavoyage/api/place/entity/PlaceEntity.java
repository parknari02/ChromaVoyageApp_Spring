package com.spring.chromavoyage.api.place.entity;

import com.spring.chromavoyage.api.place.dto.PlaceDTO;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "placelist")
public class PlaceEntity {
    //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private int placeListId;

    @Column(nullable = false)
    private int coloringLocationId;

    @Column(nullable = false)
    private int groupId;

    @Column(nullable = false)
    private String locationName;

    @Column(nullable = false, unique = true)
    private String placeName;

    @Lob
    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    public static PlaceEntity toPlaceEntity(PlaceDTO placeDTO) {
        PlaceEntity placeEntity = new PlaceEntity();
        placeEntity.setPlaceListId(placeDTO.getPlaceListId());
        placeEntity.setColoringLocationId(placeDTO.getColoringLocationId());
        placeEntity.setGroupId(placeDTO.getGroupId());
        placeEntity.setLocationName(placeDTO.getLocationName());
        placeEntity.setPlaceName(placeDTO.getPlaceName());
        placeEntity.setAddress(placeDTO.getAddress());
        placeEntity.setLatitude(placeDTO.getLatitude());
        placeEntity.setLongitude(placeDTO.getLongitude());
        return placeEntity;
    }


}
