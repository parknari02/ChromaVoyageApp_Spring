package com.spring.chromavoyage.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocialOAuthGoogleRes {

//    private String jwtToken;
    private Long userId;
    private String accessToken;
    private String email;
    private String username;
    private String picture;

//    private String tokenType;
}
