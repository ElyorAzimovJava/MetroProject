package com.example.test.dto.department;

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
public class DepartmentCreateDto {
    @NotBlank(message = "Department name null yoki bo'sh bo'lmasligi kerak")
    @Size(message = "Department nomining minimum hajmi ${min} va maksimum hajmi ${max} bo'lishi kerak", min = 3,max = 200)
    private String name;
}
