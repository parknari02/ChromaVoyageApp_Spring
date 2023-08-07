package com.spring.chromavoyage.api.profiles.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadImage {
    private String uploadImgName;
    private String storeImgName;

}
