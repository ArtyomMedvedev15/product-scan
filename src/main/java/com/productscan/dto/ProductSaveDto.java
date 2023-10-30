package com.productscan.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductSaveDto {
    private String name;
    private String description;
    private String serialNumber;
}
