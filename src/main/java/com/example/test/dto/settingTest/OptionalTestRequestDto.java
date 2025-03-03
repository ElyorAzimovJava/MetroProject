package com.example.test.dto.settingTest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionalTestRequestDto {
    private UUID id;
    private String name;
    private int count;
}
