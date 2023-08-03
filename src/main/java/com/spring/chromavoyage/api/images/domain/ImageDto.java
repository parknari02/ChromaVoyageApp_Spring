package com.spring.chromavoyage.api.images.domain;

import com.spring.chromavoyage.api.images.entity.ImageEntity;
import lombok.*;

@Data
@NoArgsConstructor
public class ImageDto {
    private Long imageId;
    private UploadFile imageFile; // originalFilename & storeFilename
    private String image_path; //pullpath
    private String file_name;
    private Long coloringLocationId;
    private Long groupId;
    private Long locationId;

    public ImageEntity toEntity() {
        return ImageEntity.builder()
                .group_id(groupId)
                .location_id(locationId)
                .coloring_location_id(coloringLocationId)
                .image_path(image_path)
                .build();
    }

}
