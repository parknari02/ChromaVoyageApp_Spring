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
    private Long placeListId;

    @Column(nullable = false)
    private Long coloringLocationId;

    @Column(nullable = false)
    private Long groupId;

    @Column(nullable = false)
    private String locationId;

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
        placeEntity.setLocationId(placeDTO.getLocationId());
        placeEntity.setPlaceName(placeDTO.getPlaceName());
        placeEntity.setAddress(placeDTO.getAddress());
        placeEntity.setLatitude(placeDTO.getLatitude());
        placeEntity.setLongitude(placeDTO.getLongitude());
        return placeEntity;
    }


}
