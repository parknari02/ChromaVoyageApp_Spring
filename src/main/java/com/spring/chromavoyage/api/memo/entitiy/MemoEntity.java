package com.spring.chromavoyage.api.memo.entitiy;
import javax.persistence.*;

import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "memolist")
public class MemoEntity {
    @Id
    @Column(name = "memolist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memoListId;

    @Column(name = "coloring_location_id")
    private Long coloringLocationId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "location_id")
    private String locationId;

    @Column(name = "memo_content", length = 100, nullable = false)
    private String memoContent;

    @Column(name = "created_date", nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;

    @Column(name = "memo_date")
    private String memoDate;


}
