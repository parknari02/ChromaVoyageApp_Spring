package com.spring.chromavoyage.api.place.dto;


import com.spring.chromavoyage.api.place.entity.PlaceEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PlaceDTO {
    private Long coloringLocationId;
    private Long groupId;
    private String locationId;
    private String placeName;
    private String address;
    private double latitude;
    private double longitude;

    private String placeDate;

    private LocalDateTime createdDate;

    private String startTime;

    private String endTime;

}
