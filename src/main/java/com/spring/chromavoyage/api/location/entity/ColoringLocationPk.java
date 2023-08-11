package com.spring.chromavoyage.api.location.entity;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class ColoringLocationPk implements Serializable {
    @Column(name = "coloring_location_id")
    private Long coloringlocationId;
    @Column(name = "group_id")
    private Long groupId;
    @Column(name = "location_id")
    private Long locationId;
}
