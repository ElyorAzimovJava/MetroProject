package com.example.test.dto.userTestCollection;

import com.example.test.dto.testter.TesterRequestDto;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTestCollectionResponseDto {
    private UUID id;
    private String title;
    private String variantFirst;
    private String variantSecond;
    private String variantThird;
    private String variantFourth;
    private Integer testNumber;
    private boolean isLastTest;
    private boolean isFirstTest;
}
