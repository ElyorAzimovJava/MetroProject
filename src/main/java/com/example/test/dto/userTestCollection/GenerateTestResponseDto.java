package com.example.test.dto.userTestCollection;

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
public class GenerateTestResponseDto {
    private Integer timeOfTest;
    private UUID testerId;
    private UUID testHistoryId;
    private List<UserTestCollectionResponseDto> tests;
}
