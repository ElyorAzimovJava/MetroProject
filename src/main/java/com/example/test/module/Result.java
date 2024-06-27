package com.example.test.module;

import com.example.test.enums.ResultType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Result  extends BaseEntity{
    private String description;
    @Enumerated(EnumType.STRING)
    private ResultType resultType;
    private Integer startPercentage;
    private Integer endPercentage;
}
