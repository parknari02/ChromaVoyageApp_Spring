package com.spring.chromavoyage.api.location.entity;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
@Data
public class UserColoringPk implements Serializable {
    private Long userColoringId;
    private Long userId;
    private Long locationId;
}
