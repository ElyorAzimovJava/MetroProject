package com.example.test.dto.position;

import lombok.*;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class PositionResponseDto {
    private UUID id;
    private String name;
    private UUID departmentId;
    private String departmentName;
    private String isForTestOrRole;
}
