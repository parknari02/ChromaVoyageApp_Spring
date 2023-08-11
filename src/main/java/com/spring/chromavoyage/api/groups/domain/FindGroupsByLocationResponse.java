package com.spring.chromavoyage.api.groups.domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class FindGroupsByLocationResponse {
    private Long groupId;

    private String groupName;

    private LocalDateTime createdDate;

    private Boolean pin;
    private Timestamp startDate;

    private Timestamp endDate;

    private List<String> groupMembers;

    private Long coloringLocationId;

    public Long getColoringLocationId() {
        return coloringLocationId;
    }

    public void setColoringLocationId(Long coloringLocationId) {
        this.coloringLocationId = coloringLocationId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getPin() {
        return pin;
    }

    public void setPin(Boolean pin) {
        this.pin = pin;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public List<String> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<String> groupMembers) {
        this.groupMembers = groupMembers;
    }
}
