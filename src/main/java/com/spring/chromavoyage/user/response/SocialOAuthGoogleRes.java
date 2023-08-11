package com.spring.chromavoyage.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocialOAuthGoogleRes {
    private Long userId;
//    private String accessToken;
    private String email;
    private String name;
    private String picture;
}
