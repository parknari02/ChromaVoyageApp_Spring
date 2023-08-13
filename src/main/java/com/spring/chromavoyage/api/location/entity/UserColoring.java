package com.spring.chromavoyage.api.location.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@IdClass(UserColoringPk.class)
@Entity
@Data

@Table(name = "user_coloring")
public class UserColoring {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_coloring_id")
    private Long userColoringId;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "location_id")
    private Long locationId;
}
