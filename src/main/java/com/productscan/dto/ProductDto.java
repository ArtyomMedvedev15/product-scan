package com.productscan.dto;

import com.productscan.entity.Product;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String serialNumber;
    private String color;
    private String category;
    private String uniqCode;
    public static ProductDto getProductDto(Product productById) {
        ProductDto productDtoById = ProductDto.builder()
                .id(productById.getId())
                .name(productById.getName())
                .description(productById.getDescription())
                .serialNumber(productById.getSerialNumber())
                .uniqCode(productById.getUniqCode())
                .color(productById.getColor())
                .category(productById.getCategory())
                .build();
        return productDtoById;
    }
}
