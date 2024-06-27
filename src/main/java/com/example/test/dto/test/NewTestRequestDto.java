package com.example.test.dto.test;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewTestRequestDto {
    @NotBlank(message = "Testning title si bo'sh bo'lmasligi kerak")
    private String title;
    @NotBlank(message = "Test variantlari null yoki bo'sh bo'lmasligi kerak")
    private String variantFirst;
    @NotBlank(message = "Test variantlari null yoki bo'sh bo'lmasligi kerak")
    private String variantSecond;
    @NotBlank(message = "Test variantlari null yoki bo'sh bo'lmasligi kerak")
    private String variantThird;
    @NotBlank(message = "Test variantlari null yoki bo'sh bo'lmasligi kerak")
    private String variantFourth;
    @NotBlank(message = "To'g'ri variant null yoki bo'sh bo'lmasligi kerak")
    private String variantTrue;
}
