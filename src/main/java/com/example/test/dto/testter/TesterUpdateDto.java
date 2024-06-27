package com.example.test.dto.testter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TesterUpdateDto {
    @NotBlank(message = "Testerning ismi null yoki bo'sh bo'lmasligi kerak")
    private String firstName;
    @NotBlank(message = "Testerning familiyasi null yoki bo'sh bo'lmasligi kerak")
    private String middleName;
    @NotBlank(message = "Testerning otasining ismi null yoki bo'sh bo'lmasligi kerak")
    private String lastName;
    @NotBlank(message = "Testerning idNumber null yoki bo'sh bo'lmasligi kerak")
    private String idNumber;
    @NotNull(message = "Testerning lavozim si null yoki bo'sh bo'lmasligi kerak")
    private UUID positionId;

}
