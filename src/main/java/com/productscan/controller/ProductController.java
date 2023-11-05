package com.productscan.controller;


import com.productscan.dto.GetAllProductDto;
import com.productscan.dto.ProductDto;
import com.productscan.dto.ProductSaveDto;
import com.productscan.entity.Product;
import com.productscan.service.api.ProductService;
import com.productscan.service.util.ProductAlreadyExistsException;
import com.productscan.service.util.ProductInvalidParameterException;
import com.productscan.service.util.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.productscan.dto.GetAllProductDto.getAllProductToDto;
import static com.productscan.dto.ProductDto.getProductDto;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public ResponseEntity<GetAllProductDto>getAllProduct(@RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size,
                                                         @RequestParam(defaultValue = "all",required = false)String category) throws ProductInvalidParameterException {
        Page<Product> listProduct;
        GetAllProductDto getAllProductDto;
        System.out.println("CATEGORY " + category);
        if(category.equals("all")) {
            log.info("Get all product in {}",new Date());
            listProduct = productService.findAll(page, size);
        }else{
            log.info("Get all product by category {} in {}",category,new Date());
            listProduct = productService.findAllByCategory(page, size,category);
        }
        getAllProductDto = getAllProductToDto(listProduct);
        log.info("Get all product with page {} and size {} in {}", page, size, new Date());
        return ResponseEntity.ok(getAllProductDto);
    }



    @GetMapping("/{idProduct}")
    public ResponseEntity<ProductDto>getProductById(@PathVariable("idProduct") Long idProduct) throws ProductNotFoundException {
        Product productById = productService.getById(idProduct);
        ProductDto productDtoById = getProductDto(productById);
        return ResponseEntity.ok(productDtoById);
    }



    @DeleteMapping("/delete/{idProduct}")
    public ResponseEntity<ProductDto>deleteProductById(@PathVariable("idProduct") Long idProduct) throws ProductNotFoundException {
        Product productById = productService.deleteProduct(idProduct);
        ProductDto productDtoById = getProductDto(productById);
        return ResponseEntity.ok(productDtoById);
    }


    @PostMapping("/save")
    public ResponseEntity<ProductDto>saveProduct(@RequestParam("data") ProductSaveDto productSaveDto) throws ProductInvalidParameterException, ProductAlreadyExistsException {
        if(!productService.existsBySerialNumber(productSaveDto.getSerialNumber())) {
            Product productSave = new Product();
            productSave.setName(productSaveDto.getName());
            productSave.setDescription(productSaveDto.getDescription());
            productSave.setSerialNumber(productSaveDto.getSerialNumber());
            productSave.setColor(productSaveDto.getColor());
            productSave.setUniqCode(productSave.getUniqCode());
            productSave.setCategory(productSave.getCategory());
            Product productSaveResult = productService.saveProduct(productSave);
            ProductDto productDtoSaveResultDto = getProductDto(productSaveResult);
            return ResponseEntity.ok(productDtoSaveResultDto);
        }else{
            throw new ProductAlreadyExistsException(String.format("Product with serial number %s already exists",productSaveDto.getSerialNumber()));
        }
    }

    @GetMapping("/serial/number/{serial_number}")
    public ResponseEntity<ProductDto>getBySerialNumber(@PathVariable("serial_number") String serial_number) throws ProductNotFoundException {
        Product productBySerialNumber = productService.findBySerialNumber(serial_number);
        ProductDto productBySerialNumberDto = getProductDto(productBySerialNumber);
        return ResponseEntity.ok(productBySerialNumberDto);
    }

}
