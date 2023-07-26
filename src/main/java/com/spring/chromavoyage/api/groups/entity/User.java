package com.spring.chromavoyage.api.groups.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "createDate")
    private LocalDateTime createDate;

    @Column(name = "email")
    private String email;

    @Column(name = "picture")
    private String picture;

    @Column(name = "provider")
    private String provider;

    @Column(name = "username")
    private String username;

    // Getter와 Setter
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserName() {
        return username;
    }
}
