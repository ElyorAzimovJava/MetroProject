package com.example.test.dto.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainTestResponse {
    private UUID id;
    private String name;
    private String positionName;
    private List<TestResponseDto> tests;
}
