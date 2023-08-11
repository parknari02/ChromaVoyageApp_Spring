package com.spring.chromavoyage.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.chromavoyage.user.model.GoogleOAuthToken;
import com.spring.chromavoyage.user.model.User;
import com.spring.chromavoyage.user.repository.UserRepository;
import com.spring.chromavoyage.user.response.SocialOAuthGoogleRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    @Value("${google.login.url}")
    private String GOOGLE_BASE_URL;
    @Value("${google.client.id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${google.redirect.uri}")
    private String GOOGLE_CALLBACK_URL;
    @Value("${google.client.secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${google.auth.scope}")
    private String GOOGLE_DATA_ACCESS_SCOPE;
    @Value("${google.token.url}")
    private String GOOGLE_TOKEN_BASE_URL;
    @Value("${google.user.info.request.url}")
    private String GOOGLE_USERINFO_REQUEST_URL;

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    @Override
    public String getGoogleOauthRedirectURL() throws UnsupportedEncodingException {
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(GOOGLE_BASE_URL)
                .queryParam("client_id", GOOGLE_CLIENT_ID)
                .queryParam("response_type", "code")
                .queryParam("scope","profile%20email")
                .queryParam("redirect_uri", URLEncoder.encode(GOOGLE_CALLBACK_URL, "UTF-8"))
                .build();

        return uriComponents.toString();
    }




    private ResponseEntity<String> requestAccessToken(String code) {
        String GOOGLE_TOKEN_REQUEST_URL="https://oauth2.googleapis.com/token";

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_CLIENT_ID);
        params.put("client_secret", GOOGLE_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity=restTemplate.postForEntity(GOOGLE_TOKEN_REQUEST_URL,
                params, String.class);

        if(responseEntity.getStatusCode()== HttpStatus.OK){
            return responseEntity;
        }
        throw new RuntimeException("non-token");
    }

    private GoogleOAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        System.out.println("response.getBody() = " + response.getBody());
        GoogleOAuthToken googleOAuthToken= objectMapper.readValue(response.getBody(), GoogleOAuthToken.class);
        System.out.println("googleOAuthToken" + googleOAuthToken.getAccess_token());
        return googleOAuthToken;
    }

    @Override
    @Transactional
    public SocialOAuthGoogleRes oAuthLogin(String code) throws IOException {

        //구글로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아옴
        ResponseEntity<String> accessTokenResponse = requestAccessToken(code);
        //응답 객체가 JSON형식으로 되어 있으므로, 이를 deserialization해서 자바 객체에 담을 것이다.
        GoogleOAuthToken oAuthToken = getAccessToken(accessTokenResponse);
        System.out.println("oAuthToken" + oAuthToken.getAccess_token());
        //액세스 토큰을 다시 구글로 보내 구글에 저장된 사용자 정보가 담긴 응답 객체를 받아온다.
        ResponseEntity<String> userInfoResponse = requestUserInfo(oAuthToken);
        //다시 JSON 형식의 응답 객체를 자바 객체로 역직렬화한다.
        User googleUser = getUserInfo(userInfoResponse);
        String userEmail = googleUser.getEmail();

        //우리 서버의 db와 대조하여 해당 user가 존재하는 지 확인한다.
        if (userRepository.existsByEmail(userEmail)) {
            //서버에 user가 존재하면 앞으로 회원 인가 처리를 위한 jwtToken을 발급한다.
//            String jwtToken=jwtService.createJwt(user_num,user_id);
            //액세스 토큰과 jwtToken, 이외 정보들이 담긴 자바 객체를 다시 전송한다.
            User foundUser = userRepository.findUserByEmail(userEmail);
            if(foundUser.getUsername() != googleUser.getUsername()){
                foundUser.updateNm(googleUser.getUsername());
            }
            SocialOAuthGoogleRes socialOAuthGoogleRes = new SocialOAuthGoogleRes(foundUser.getUserId(), oAuthToken.getAccess_token(), foundUser.getEmail(), foundUser.getUsername(), foundUser.getPicture());
            return socialOAuthGoogleRes;
        } else {
            userRepository.save(googleUser);
            User foundUser = userRepository.findUserByEmail(googleUser.getEmail());
            SocialOAuthGoogleRes socialOAuthGoogleRes = new SocialOAuthGoogleRes(foundUser.getUserId(), oAuthToken.getAccess_token(), foundUser.getEmail(), foundUser.getUsername(), foundUser.getPicture());
            return socialOAuthGoogleRes;
        }
    }

    private ResponseEntity<String> requestUserInfo(GoogleOAuthToken oAuthToken) {
        String url = "https://www.googleapis.com/oauth2/v1/userinfo";

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + oAuthToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
        System.out.println(request.getHeaders().get("Authorization"));
        return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
    }

    public User getUserInfo(ResponseEntity<String> userInfoRes) throws JsonProcessingException {
        System.out.println("userInfoRes" + userInfoRes.getBody());
        User googleUser=objectMapper.readValue(userInfoRes.getBody(), User.class);
        return googleUser;
    }
}
