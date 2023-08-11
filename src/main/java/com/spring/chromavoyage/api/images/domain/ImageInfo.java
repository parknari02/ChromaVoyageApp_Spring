package com.spring.chromavoyage.api.images.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImageInfo {

    private Long imageId;
    private String image_path;
    private String file_name;

}
