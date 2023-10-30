package com.productscan.controller;


import com.productscan.dto.GetAllProductDto;
import com.productscan.entity.Product;
import com.productscan.service.ProductService;
import com.productscan.service.util.ProductInvalidParameterException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {


    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<GetAllProductDto>getAllProduct( @RequestParam(defaultValue = "1") int page,
                                                          @RequestParam(defaultValue = "10") int size) throws ProductInvalidParameterException {
        Page<Product> listProduct = productService.findAll(page, size);
        GetAllProductDto getAllProductDto = GetAllProductDto.builder()
                .currentPage(listProduct.getNumber())
                .totalPage(listProduct.getTotalPages())
                .data(listProduct.getContent())
                .build();
        log.info("Get all product with page {} and size {} in {}",page,size,new Date());
        return ResponseEntity.ok(getAllProductDto);

    }

}
