package com.example.test.dto.testter;

import com.example.test.dto.position.PositionResponseDto;
import com.example.test.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TesterResponse {
    private UUID id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String idNumber;
    private String passportSerial;
    private UserType role;
    private PositionResponseDto position;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
