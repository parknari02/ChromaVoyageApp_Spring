package com.spring.chromavoyage.api.groups.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "`GROUP`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
//    @SequenceGenerator(name = "my_seq", sequenceName = "my_sequence", allocationSize = 1)
    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    private Boolean pin;

    // Getter와 Setter
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

    // 기본 생성자
    public Group() {
    }

    // 필요한 생성자
    public Group(String groupName, LocalDateTime createdDate, Boolean pin) {
        this.groupName = groupName;
        this.createdDate = createdDate;
        this.pin = pin;
    }
}

//@Entity
//public class Group {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "group_id")
//    private Long groupId;
//
//    @Column(name = "group_name")
//    private String groupName;
//
//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//
//    private Boolean pin;
//
//    public Long getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(Long groupId) {
//        this.groupId = groupId;
//    }
//
//    public String getGroupName() {
//        return groupName;
//    }
//
//    public void setGroupName(String groupName) {
//        this.groupName = groupName;
//    }
//
//    public LocalDateTime getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(LocalDateTime createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public Boolean getPin() {
//        return pin;
//    }
//
//    public void setPin(Boolean pin) {
//        this.pin = pin;
//    }
//}


