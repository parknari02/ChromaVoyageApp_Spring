package com.spring.chromavoyage.api.images.entity;

import com.spring.chromavoyage.api.images.domain.ImageDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Table(name="image")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@IdClass(ImageId.class)
public class ImageEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="image_id")
    private Long imageId;

    @Column(nullable = false)
    private String image_path;

    @Column(name="file_name")
    private String file_name;

    @Id
    @Column(name="group_id")
    private Long groupId;

    @Id
    @Column(name="location_id")
    private Long locationId;

    @Id
    @Column(name="coloring_location_id")
    private Long coloringLocationId;

    @Builder
    public ImageEntity(String image_path, Long group_id, Long location_id, Long coloring_location_id) {
        this.image_path = image_path;
        this.groupId = group_id;
        this.locationId = location_id;
        this.coloringLocationId = coloring_location_id;
    }

}
