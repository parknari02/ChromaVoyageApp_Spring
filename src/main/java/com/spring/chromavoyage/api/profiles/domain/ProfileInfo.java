package com.spring.chromavoyage.api.profiles.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileInfo {
    private String user_name;
    private String profileImg_path;
}
