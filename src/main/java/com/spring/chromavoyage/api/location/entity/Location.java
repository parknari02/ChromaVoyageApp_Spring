package com.spring.chromavoyage.api.location.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "location")
public class Location {
    @Id
    @Column(name = "location_id")
    private Long locationId;

    @Column(name = "location_name")
    private String locationName;

    public String getLocationName() {
        return locationName;
    }

    public Long getLocationId() {
        return locationId;
    }
}
