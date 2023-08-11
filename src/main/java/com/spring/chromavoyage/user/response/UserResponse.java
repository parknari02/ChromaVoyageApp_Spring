package com.spring.chromavoyage.user.response;

import lombok.Getter;

@Getter
public class UserResponse<T> {
    private Object result;
    public UserResponse(Object result){
        this.result = result;
    }
}
