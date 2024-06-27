package com.example.test.dto.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestResponseDto {
    private UUID id;
    private String title;
    private String variantFirst;
    private String variantSecond;
    private String variantThird;
    private String variantFourth;
    private String variantTrue;
    private Integer code;
}
