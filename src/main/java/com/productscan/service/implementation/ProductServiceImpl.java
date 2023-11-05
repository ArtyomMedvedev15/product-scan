package com.productscan.service.implementation;

import com.productscan.entity.Product;
import com.productscan.repository.ProductRepository;
import com.productscan.service.api.ProductService;
import com.productscan.service.util.ProductInvalidParameterException;
import com.productscan.service.util.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ResourceLoader resourceLoader;

    @Override
    public Product saveProduct(Product productSave) {
        if(productSave!=null){
            Product productSaveResult = productRepository.save(productSave);
            log.info("Save new product with serial number - {} in {}",productSaveResult.getSerialNumber(),new Date());
            return productSaveResult;
        }else{
            log.error("Cannot save new product, parameter equal null in {}", new Date());
            return null;
        }
    }

    @Override
    public Product updateProduct(Product productUpdate) {
        if(productUpdate!=null){
            Product productSaveResult = productRepository.save(productUpdate);
            log.info("Save new product with serial number - {} in {}",productSaveResult.getSerialNumber(),new Date());
            return productSaveResult;
        }else{
            log.error("Cannot save new product, parameter equal null in {}", new Date());
            return null;
        }
    }

    @Override
    public Product deleteProduct(Long idProductDelete) throws ProductNotFoundException {
        Optional<Product> productByID = productRepository.findById(idProductDelete);
        if(productByID.isPresent()){
            Product productDelete = productByID.get();
            productRepository.delete(productDelete);
            log.warn("Delete product with id {} in {}",productDelete.getId(),new Date());
            return productDelete;
        }else{
            log.error("Product with id {} was not found in {}",idProductDelete,new Date());
            throw new ProductNotFoundException(String.format("Product with id %s was not found",idProductDelete));
        }
    }

    @Override
    public Product getById(Long idProduct) throws ProductNotFoundException {
        Optional<Product> productByID = productRepository.findById(idProduct);
        if(productByID.isPresent()){
            log.info("Get product by with id {} in {}",idProduct,new Date());
            return productByID.get();
        }else{
            log.error("Product with id {} was not found in {}",idProduct,new Date());
            throw new ProductNotFoundException(String.format("Product with id %s was not found",idProduct));
        }
    }

    @Override
    public Product findBySerialNumber(String serialNumber) throws ProductNotFoundException {
        Optional<Product> productBySerialNumber = Optional.ofNullable(productRepository.findBySerialNumber(serialNumber));
        if(productBySerialNumber.isPresent()){
            log.info("Get product with serial number {} in {}",serialNumber,new Date());
            return productBySerialNumber.get();
        }else{
            log.error("Product with serial number {} was not found in {}",serialNumber,new Date());
            throw new ProductNotFoundException(String.format("Product with serial number %s was not found",serialNumber));
        }
    }

    @Override
    public Page<Product> findAll(int page, int size) throws ProductInvalidParameterException {
        if(page>=0 && size>0) {
            Pageable pageable = PageRequest.of(page, size);
            Page<Product> listOfProduct = productRepository.findAll(pageable);
            log.info("Get all product with total item {} in {}",listOfProduct.getTotalElements(),new Date());
            return listOfProduct;
        }else {
            log.error("Invalid parameter page - {} or size - {} in {}",page,size,new Date());
            throw new ProductInvalidParameterException("Invalid page or size, try yet");
        }
    }

    @Override
    public boolean existsBySerialNumber(String serialNumber) throws ProductInvalidParameterException {
        if(!serialNumber.equals("")){
            log.info("Check exists product by serial number with {} in {}",serialNumber,new Date());
            return productRepository.existsBySerialNumber(serialNumber);
        }else{
            log.error("Invalid serial number in {}",new Date());
            throw new ProductInvalidParameterException("Invalid serial number, try yet");
        }

    }

    @Override
    public void saveFile(MultipartFile imageFile, Product productSave) throws IOException {
        String relativePath = "uploads/";
        String projectDirectory = System.getProperty("user.dir");
        String saveDirectory = Paths.get(projectDirectory, relativePath).toString();
        File directory = new File(saveDirectory);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + imageFile.getOriginalFilename();
        File uploadFile = new File(saveDirectory, resultFileName);
        imageFile.transferTo(uploadFile);
        }

}
