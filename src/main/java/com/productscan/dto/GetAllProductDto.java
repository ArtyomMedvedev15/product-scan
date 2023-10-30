package com.productscan.dto;

import com.productscan.entity.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetAllProductDto {
    private PaginationModelDto pagination;
    private List<Product> data;
}
