package com.example.test.dto.user;

import com.example.test.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private UserType role;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
