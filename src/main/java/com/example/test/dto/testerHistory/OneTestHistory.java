package com.example.test.dto.testerHistory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OneTestHistory {
    private UUID id;
    private String title;
    private Integer testNumber;
    private Boolean isCorrect;
    private String selectedVariant;
}
