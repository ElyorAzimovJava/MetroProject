package com.example.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomPagination<T> {
    private Integer totalElements;
    private Integer currentPage;
    private Integer size;
    private Integer totalPages;
    private List<T> data;
    private Boolean isFirstPage;
    private Boolean isLastPage;
}
