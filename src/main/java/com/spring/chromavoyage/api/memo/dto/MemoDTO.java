package com.spring.chromavoyage.api.memo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class MemoDTO {
    private Long coloringLocationId;
    private Long groupId;

    private String locationId;
    private String memoListId;
    private String memoContent;
    private String startTime;
    private String endTime;

}




