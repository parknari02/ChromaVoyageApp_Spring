package com.spring.chromavoyage.api.groups.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ColoringLocationPk implements Serializable {
    private Long coloringLocationId;
    private Long groupId;
    private Long locationId;
}