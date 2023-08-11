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

        ResponseEntity<String> accessTokenResponse = requestAccessToken(code);

        GoogleOAuthToken oAuthToken = getAccessToken(accessTokenResponse);
        System.out.println("oAuthToken" + oAuthToken.getAccess_token());

        ResponseEntity<String> userInfoResponse = requestUserInfo(oAuthToken);

        User googleUser = getUserInfo(userInfoResponse);
        String userEmail = googleUser.getEmail();

        //우리 서버의 db와 대조하여 해당 user가 존재하는 지 확인
        if (userRepository.existsByEmail(userEmail)) {

            User foundUser = userRepository.findByEmail(userEmail);

            if(foundUser.getUsername() != googleUser.getUsername()){
                foundUser.updateNm(googleUser.getUsername());
            }

            SocialOAuthGoogleRes socialOAuthGoogleRes = new SocialOAuthGoogleRes(foundUser.getUserId(), foundUser.getEmail(), foundUser.getUsername(), foundUser.getPicture()); // oAuthToken.getAccess_token(),
            return socialOAuthGoogleRes;
        } else {
            userRepository.save(googleUser);
            User foundUser = userRepository.findByEmail(googleUser.getEmail());

            SocialOAuthGoogleRes socialOAuthGoogleRes = new SocialOAuthGoogleRes(foundUser.getUserId(), foundUser.getEmail(), foundUser.getUsername(), foundUser.getPicture()); //oAuthToken.getAccess_token(),
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
