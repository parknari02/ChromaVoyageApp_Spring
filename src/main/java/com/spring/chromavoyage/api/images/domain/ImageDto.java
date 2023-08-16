package com.spring.chromavoyage.api.images.domain;

import com.spring.chromavoyage.api.images.entity.ImageEntity;
import lombok.*;

@Data
@NoArgsConstructor
public class ImageDto {
    private Long imageId;
    private UploadFile imageFile; // originalFilename & storeFilename & path
    private Long coloringLocationId;
    private Long groupId;
    private Long locationId;

    public ImageEntity toEntity() {
        return ImageEntity.builder()
                .image_path(imageFile.getImageUrl())
                .file_name(imageFile.getUploadFileName())
                .group_id(groupId)
                .location_id(locationId)
                .coloring_location_id(coloringLocationId)
                .build();
    }

}
