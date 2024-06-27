package com.example.test.dto.testter;

import com.example.test.dto.department.DepartmentResponseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TesterRequestDto {
    @NotBlank(message = "Ism bo'sh yoki null bo'lmasligi kerak")
    private String firstName;
    @NotBlank(message = "Familiya bo'sh yoki null bo'lmasligi kerak")
    private String middleName;
    @NotBlank(message = "Otasining ismi bo'sh yoki null bo'lmasligi kerak")
    private String lastName;
    @NotBlank(message = "Id raqam  bo'sh yoki null bo'lmasligi kerak")
    private String idNumber;
    @NotBlank(message = "Parol null yoki bosh bolmaligi kerak")
    @Size(message = "Paroling uzunligi minimum ", min = 6,max = 20)
    @NotBlank(message = "Passport seria null yoki bo'sh bo'lmasligi kerak")
    @Pattern(regexp = "^[A-Z]{2}[0-9]{7}$",message = "Noto'g'ri passport Seria va raqam")
    private String passportSerial;
    private String password;
    private String confirmPassword;
    @NotNull(message = "Department Id null bo'lmasligi kerak ")
    private UUID departmentId;
    @NotNull(message = " Lavozim Id si null bo'lmasligi kerak ")
    private UUID positionId;
}
