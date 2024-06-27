package com.example.test.dto.test;



import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MainTestRequestDto {
    @NotBlank(message = "File nomi bo'sh bo'lmasligi kerak ")
    private String name;
    @NotNull(message = "File name null bo'lmasligi kerak")
    private MultipartFile file;
    @NotNull(message = "Position name null bo'lmasligi kerak")
    private String positionName;
}
