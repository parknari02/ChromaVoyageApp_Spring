package com.spring.chromavoyage.api.location.entity;
import lombok.Data;
import java.io.Serializable;
@Data
public class UserColoringPk implements Serializable {
    private Long usercoloringId;
    private Long userId;
    private Long locationId;
}
