package com.productscan.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaginationModelDto {
    private Integer currentPage;
    private Integer totalPages;
}
