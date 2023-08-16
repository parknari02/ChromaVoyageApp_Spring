package com.spring.chromavoyage.api.profiles.domain;

import com.spring.chromavoyage.api.images.domain.UploadFile;
import com.spring.chromavoyage.api.profiles.entity.ProfileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDto {
    private Long user_id;
    private String user_name;
    private String profileImg_path;
    private UploadFile imageFile;

    public ProfileEntity toEntity() {
        return ProfileEntity.builder()
                .user_name(user_name)
                .profileImg_path(profileImg_path)
                .build();
    }

}
