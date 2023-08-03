package com.spring.chromavoyage.api.images.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ImageId implements Serializable {
    private Long imageId;
    private Long groupId;
    private Long locationId;
    private Long coloringLocationId;


}
