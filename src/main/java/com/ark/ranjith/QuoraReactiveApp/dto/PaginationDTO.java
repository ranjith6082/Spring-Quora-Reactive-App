package com.ark.ranjith.QuoraReactiveApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDTO {
    private long totalRecords;
    private int currentPage;
    private int totalPages;
    private Integer nextPage;
    private Integer prevPage;
}
