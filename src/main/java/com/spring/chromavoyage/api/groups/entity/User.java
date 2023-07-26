package com.spring.chromavoyage.api.groups.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "createDate")
    private LocalDateTime createDate;


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

}
