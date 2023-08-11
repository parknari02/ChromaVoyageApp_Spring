package com.spring.chromavoyage.user.controller;
import com.spring.chromavoyage.user.response.SocialOAuthGoogleRes;
import com.spring.chromavoyage.user.response.UserResponse;
import com.spring.chromavoyage.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public UserResponse<SocialOAuthGoogleRes> loginGoogle (@RequestParam(name = "code") String code, HttpServletRequest request)throws IOException{
        System.out.println(">> 소셜 로그인 API 서버로부터 받은 code :"+ code);
        SocialOAuthGoogleRes socialOAuthGoogleRes =userService.oAuthLogin(code);

        HttpSession session = request.getSession();

        session.setAttribute("userId", socialOAuthGoogleRes.getUserId());
        session.setAttribute("user", socialOAuthGoogleRes);
        session.setMaxInactiveInterval(-1);//세션 무제한
        return new UserResponse<>(socialOAuthGoogleRes);
    }

    @GetMapping(value = "/logout")
    public UserResponse<String> logoutGoogle (HttpServletRequest request) throws IOException, NoSuchFieldException, IllegalAccessException {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("userId"));
        session.invalidate();
        return new UserResponse<>("로그아웃 되었습니다.");
    }

}
