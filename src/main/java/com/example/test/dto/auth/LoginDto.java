package com.example.test.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    @NotBlank(message = "Email yoki id raqam null yoki bosh bolmaligi kerak")
    private String email;
    @NotBlank(message = "Parol null yoki bosh bolmaligi kerak")
    @Size(message = "Paroling uzunligi minimum ", min = 6,max = 20)
    private String password;
}
