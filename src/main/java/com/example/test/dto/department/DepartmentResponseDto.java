package com.example.test.dto.department;

import com.example.test.dto.position.PositionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentResponseDto {
    private UUID id;
    private String name;
    private List<PositionResponseDto> positions;
    private LocalDate createdDate;
    private LocalDate updatedDate;
}
