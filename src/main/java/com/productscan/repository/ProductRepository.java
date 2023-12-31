package com.productscan.repository;

import com.productscan.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Page<Product>findAll(Pageable pageable);
    Product findBySerialNumber(String serialNumber);
    boolean existsBySerialNumber(String serialNumber);
    Page<Product>findAllByCategory(Pageable pageable,String category);
}
