package com.example.test.dto.settingTest;

import com.example.test.dto.position.PositionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionalTestResponseDto {
    private UUID id;
    private  PositionResponseDto positionResponseDto;
    private int count;
}
