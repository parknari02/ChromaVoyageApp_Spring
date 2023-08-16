package com.spring.chromavoyage.api.images.entity;

import lombok.*;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ImageId implements Serializable {
    @Column(name="image_id")
    private Long imageId;
    @Column(name="group_id")
    private Long groupId;
    @Column(name="location_id")
    private Long locationId;
    @Column(name="coloring_location_id")
    private Long coloringLocationId;


}
