package com.spring.chromavoyage.api.location.entity;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
@Data
public class UserColoringPk implements Serializable {
    @Column(name = "user_coloring_id")
    private Long usercoloringId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "location_id")
    private Long locationId;
}
