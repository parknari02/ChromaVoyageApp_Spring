package com.spring.chromavoyage.api.profiles.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="user")
@Data
@NoArgsConstructor
@Entity
public class ProfileEntity {
    @Id
    @Column(name="user_id")
    private Long userId;

    @Column(name="username")
    private String user_name;

    @Column(name="picture")
    private String profileImg_path;

    @Builder
    public ProfileEntity(String user_name, String profileImg_path) {
        this.user_name = user_name;
        this.profileImg_path = profileImg_path;
    }

}
