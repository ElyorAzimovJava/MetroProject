package com.example.test.dto.position;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PositionRequestDto {
    @NotBlank(message = "Lavozim nomi null yoki bo'sh bo'lmaligi kerak")
    private String name;
}
