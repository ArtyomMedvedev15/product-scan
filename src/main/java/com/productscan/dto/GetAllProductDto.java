package com.productscan.dto;

import com.productscan.entity.Product;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@Builder
public class GetAllProductDto {
    private PaginationModelDto pagination;
    private List<Product> data;

    public static GetAllProductDto getAllProductToDto(Page<Product> listProduct) {
        GetAllProductDto getAllProductDto;
        PaginationModelDto paginationModelDto = PaginationModelDto.builder()
                .currentPage(listProduct.getNumber())
                .totalPages(listProduct.getTotalPages())
                .build();
        getAllProductDto = GetAllProductDto.builder()
                .pagination(paginationModelDto)
                .data(listProduct.getContent())
                .build();
        return getAllProductDto;
    }
}
