package com.example.test.dto.userTestCollection;

import lombok.Data;

import java.util.UUID;

@Data
public class UserTestCollectionRequestDto {
    private UUID id;
    private Integer testNumber;
    private String selectVariant;
}
