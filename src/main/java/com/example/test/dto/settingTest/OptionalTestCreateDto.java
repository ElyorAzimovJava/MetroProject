package com.example.test.dto.settingTest;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionalTestCreateDto {
    @NotBlank(message = "Optionlat position nomi null yoki bo'sh bo'lmasligi kerak")
    private String name;
}
