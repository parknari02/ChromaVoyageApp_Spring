package com.spring.chromavoyage.api.place.entity;

import com.spring.chromavoyage.api.groups.entity.ColoringLocationPk;
import com.spring.chromavoyage.api.place.dto.PlaceDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@IdClass(PlacePk.class)
@Entity
@Data
@Table(name = "placelist")
public class PlaceEntity {
    //
    @Id
    @Column(name = "placelist_id")
    @GeneratedValue(strategy = GenerationType.AUTO) // auto_increment
    private Long placeListId;

    @Id
    @Column(name = "coloring_location_id")
    private Long coloringLocationId;

    @Id
    @Column(name = "group_id")
    private Long groupId;

    @Id
    @Column(name = "location_id")
    private String locationId;

    @Column(name = "created_date", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;

    @Column(name = "place_date")
    private String placeDate;


    @Column(name = "place_name")
    private String placeName;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

}
