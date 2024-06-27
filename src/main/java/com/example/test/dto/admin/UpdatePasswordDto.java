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
public class UpdatePasswordDto {
    @NotBlank(message = "Eski parol null yoki bo'sh bo'lmasligi kerak")
    private String oldPassword;
    @NotBlank(message = "Yangi parol null yoki bo'sh bo'lmasligi kerak")
    private String newPassword;
    @NotBlank(message = "Tasdiqlash paroli null yoki bo'sh bo'lmasligi kerak")
    private String confirmPassword;
}
