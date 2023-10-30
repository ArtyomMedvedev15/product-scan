package com.productscan.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private String serialNumber;
    private String photoUrl;
}
