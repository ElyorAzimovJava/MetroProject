package com.example.test.dto.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserRequestDto {
    @NotBlank(message = "Ism null yoki bo'sh bo'lmasligi kerak")
    private String firstName;
    @NotBlank(message = "Familiya null yoki bo'sh bo'lmasligi kerak")
    private String lastName;
    @NotBlank(message = "Email null yoki bo'sh bo'lmasligi kerak")
    private String email;
}
