package com.example.test.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentUpdateDto {
    @NotNull(message = "Department Id null bo'lmasligi kerak")
    private UUID departmentId;
    @NotBlank(message = "Department name null yoki bo'sh bo'lmasligi kerak")
    @Size(message = "Department nomining minimum hajmi ${min} va maksimum hajmi ${max} bo'lishi kerak", min = 3,max = 200)
    private String newName;
}
