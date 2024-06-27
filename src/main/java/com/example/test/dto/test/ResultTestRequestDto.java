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
public class ResultTestRequestDto {
    private UUID id;
    private String selectedVariant;
    private Boolean result;
}
