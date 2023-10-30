package com.productscan.controller;


import com.productscan.dto.GetAllProductDto;
import com.productscan.dto.PaginationModelDto;
import com.productscan.dto.ProductDto;
import com.productscan.entity.Product;
import com.productscan.service.ProductService;
import com.productscan.service.util.ProductInvalidParameterException;
import com.productscan.service.util.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        PaginationModelDto paginationModelDto  = PaginationModelDto.builder()
                .currentPage(listProduct.getNumber())
                .totalPages(listProduct.getTotalPages())
                .build();
        GetAllProductDto getAllProductDto = GetAllProductDto.builder()
                .pagination(paginationModelDto)
                .data(listProduct.getContent())
                .build();
        log.info("Get all product with page {} and size {} in {}",page,size,new Date());
        return ResponseEntity.ok(getAllProductDto);
    }

    @GetMapping("/{idProduct}")
    public ResponseEntity<ProductDto>getProductById(@PathVariable("idProduct") Long idProduct) throws ProductNotFoundException {
        Product productById = productService.getById(idProduct);
        ProductDto productDtoById = ProductDto.builder()
                .id(productById.getId())
                .name(productById.getName())
                .description(productById.getDescription())
                .serialNumber(productById.getSerialNumber())
                .photoUrl(productById.getPhotoUrl())
                .build();
        return ResponseEntity.ok(productDtoById);
    }

    @DeleteMapping("/delete/{idProduct}")
    public ResponseEntity<ProductDto>deleteProductById(@PathVariable("idProduct") Long idProduct) throws ProductNotFoundException {
        Product productById = productService.deleteProduct(idProduct);
        ProductDto productDtoById = ProductDto.builder()
                .id(productById.getId())
                .name(productById.getName())
                .description(productById.getDescription())
                .serialNumber(productById.getSerialNumber())
                .photoUrl(productById.getPhotoUrl())
                .build();
        return ResponseEntity.ok(productDtoById);
    }

}
