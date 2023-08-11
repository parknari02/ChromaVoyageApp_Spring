package com.spring.chromavoyage.api.location.domain;

import java.sql.Timestamp;
import java.util.List;

public class AddLocationsRequest {
    private Long userId;

    private List<String> locationName;

    private Timestamp startDate;
    private Timestamp endDate;

    public Long getUserId() {
        return userId;
    }

    public List<String> getLocationName() {
        return locationName;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }
}
