package com.spring.chromavoyage.api.location.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
@IdClass(ColoringLocationPk.class)
@Entity
@Data
@Table(name = "coloring_location")
public class ColoringLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coloring_location_id")
    private Long coloringlocationId;

    @Id
    @Column(name = "group_id")
    private Long groupId;

    @Id
    @Column(name = "location_id")
    private Long locationId;

    @CreationTimestamp
    @Column(name = "start_date")
    private Timestamp startDate;

    @CreationTimestamp
    @Column(name = "end_date")
    private Timestamp endDate;


}
