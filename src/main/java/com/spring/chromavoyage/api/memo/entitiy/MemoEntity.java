package com.spring.chromavoyage.api.memo.entitiy;
import javax.persistence.*;

import lombok.Data;

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

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;
}
