package com.spring.chromavoyage.api.groups.domain;

public class FindGroupsByLocationRequest {
    private Long userId;
    private String locationName;

    public Long getUserId() {
        return this.userId;
    }

    public String getLocationName() {
        return locationName;
    }
}
