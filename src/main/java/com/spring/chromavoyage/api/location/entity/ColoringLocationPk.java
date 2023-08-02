package com.spring.chromavoyage.api.location.entity;
import lombok.Data;
import java.io.Serializable;

@Data
public class ColoringLocationPk implements Serializable {
    private Long coloringlocationId;
    private Long groupId;
    private Long locationId;
}
