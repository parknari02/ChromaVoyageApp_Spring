package com.spring.chromavoyage.user.controller;

import com.spring.chromavoyage.user.response.SocialOAuthGoogleRes;
import com.spring.chromavoyage.user.response.UserResponse;
import com.spring.chromavoyage.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@RestController
public class AccountController {
    @Autowired
    UserService userService;

    @GetMapping("/auth/google")
    public UserResponse<String> socialLoginRedirect() throws IOException {
        return new UserResponse<>(userService.getGoogleOauthRedirectURL());
    }

//    @ResponseBody
    @GetMapping(value = "/login")
    public UserResponse<SocialOAuthGoogleRes> loginGoogle (@RequestParam(name = "code") String code)throws IOException{
        System.out.println(">> 소셜 로그인 API 서버로부터 받은 code :"+ code);
        SocialOAuthGoogleRes socialOAuthGoogleRes =userService.oAuthLogin(code);
        return new UserResponse<>(socialOAuthGoogleRes);
    }

}
