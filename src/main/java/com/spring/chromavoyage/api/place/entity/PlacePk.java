package com.spring.chromavoyage.api.place.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class PlacePk implements Serializable {
    private Long placeListId;
    private Long coloringLocationId;
    private Long groupId;
    private String locationId;
}
