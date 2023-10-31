package com.productscan.service.api;

import com.productscan.entity.Product;
import com.productscan.service.util.ProductInvalidParameterException;
import com.productscan.service.util.ProductNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface ProductService {
    Product saveProduct(Product productSave);
    Product updateProduct(Product productUpdate);
    Product deleteProduct(Long idProductDelete) throws ProductNotFoundException;
    Product getById(Long idProduct) throws ProductNotFoundException;
    Product findBySerialNumber(String serialNumber) throws ProductNotFoundException;
    Page<Product> findAll(int page, int size) throws ProductInvalidParameterException;
    boolean existsBySerialNumber(String serialNumber) throws ProductInvalidParameterException;
    void saveFile(MultipartFile imageFile, Product productSave) throws IOException;
}
