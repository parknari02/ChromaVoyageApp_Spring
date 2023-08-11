package com.spring.chromavoyage.api.groups.service;

import org.springframework.http.HttpStatus;

public class UserInvitationException extends Exception {
    private final String responseCode;
    private final String description;
    private final HttpStatus httpStatus;

    public UserInvitationException(String responseCode, String description) {
        this.responseCode = responseCode;
        this.description = description;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR; // 기본적으로 서버 오류 상태 코드를 반환합니다.
    }

    public UserInvitationException(String responseCode, String description, HttpStatus httpStatus) {
        this.responseCode = responseCode;
        this.description = description;
        this.httpStatus = httpStatus;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getDescription() {
        return description;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
