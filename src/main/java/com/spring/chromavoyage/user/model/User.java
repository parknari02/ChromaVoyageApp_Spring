package com.spring.chromavoyage.user.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@Getter
@Entity(name = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;
    private String username;
    private String email;
    private String provider;
    private String picture;
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    @Column(name = "create_date")
    private Timestamp createDate;

    public void updateNmAndPicture(String username, String picture){
        this.username = username;
        this.picture = picture;
    }
    public void updateProvider(String provider){
        this.provider = provider;
    }
}
