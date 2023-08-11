package com.spring.chromavoyage.user.service;

import com.spring.chromavoyage.user.response.SocialOAuthGoogleRes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface UserService{
    String getGoogleOauthRedirectURL() throws UnsupportedEncodingException;
    SocialOAuthGoogleRes oAuthLogin(String code) throws IOException;
}
