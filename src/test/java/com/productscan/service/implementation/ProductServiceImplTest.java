package com.productscan.service.implementation;

import com.productscan.entity.Product;
import com.productscan.repository.ProductRepository;
import com.productscan.service.api.ProductService;
import com.productscan.service.util.ProductInvalidParameterException;
import com.productscan.service.util.ProductNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Testcontainers
@Sql(value = "classpath:/sql/clearData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:/sql/clearData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProductServiceImplTest {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.0-alpine3.18")
            .withDatabaseName("prop")
            .withUsername("postgres")
            .withPassword("postgres")
            .withExposedPorts(5432)
            .withInitScript("sql/initDB.sql");


    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url",
                () -> String.format("jdbc:postgresql://localhost:%d/prop", postgreSQLContainer.getFirstMappedPort()));
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "postgres");
    }
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    void SaveProductTest_ReturnTrue() {
        Product productSave = new Product();
        productSave.setName("Test");
        productSave.setDescription("Test");
        productSave.setSerialNumber("Test");

        when(productRepository.save(productSave)).thenReturn(productSave);

        Product productSaveResult = productService.saveProduct(productSave);

        Assert.assertEquals("Test", productSaveResult.getName());

        verify(productRepository,times(1)).save(productSave);

    }


    @Test
    void UpdateProductTest_ReturnTrue() {
        Product productUpdate = new Product();
        productUpdate.setId(123L);
        productUpdate.setName("Test");
        productUpdate.setDescription("Test");
        productUpdate.setSerialNumber("Test");

        when(productRepository.save(productUpdate)).thenReturn(productUpdate);

        Product productSaveResult = productService.saveProduct(productUpdate);

        Assert.assertEquals("Test", productSaveResult.getName());

        verify(productRepository,times(1)).save(productUpdate);
    }

    @Test
    void DeleteProductTest_ReturnTrue() throws ProductNotFoundException {
        Product productDelete = new Product();
        productDelete.setId(123L);
        productDelete.setName("Test");
        productDelete.setDescription("Test");
        productDelete.setSerialNumber("Test");

        when(productRepository.findById(123L)).thenReturn(Optional.of(productDelete));

        productService.deleteProduct(123L);

        verify(productRepository,times(1)).delete(productDelete);
        verify(productRepository,times(1)).findById(123L);

    }


    @Test
    void GetByIdTest_ReturnTrue() throws ProductNotFoundException {
        Product productbyID = new Product();
        productbyID.setId(123L);
        productbyID.setName("Test");
        productbyID.setDescription("Test");
        productbyID.setSerialNumber("Test");

        when(productRepository.findById(123L)).thenReturn(Optional.of(productbyID));

        Product productServiceById = productService.getById(123L);

        Assert.assertEquals("Test",productServiceById.getName());

        verify(productRepository,times(1)).findById(123L);

    }

    @Test
    void FindBySerialNumberTest_ReturnTrue() throws ProductNotFoundException {
        Product productBySerialNumber = new Product();
        productBySerialNumber.setId(123L);
        productBySerialNumber.setName("Test");
        productBySerialNumber.setDescription("Test");
        productBySerialNumber.setSerialNumber("Test");

        when(productRepository.findBySerialNumber("Test")).thenReturn(productBySerialNumber);

        Product productServiceById = productService.findBySerialNumber("Test");

        Assert.assertEquals("Test",productServiceById.getSerialNumber());

        verify(productRepository,times(1)).findBySerialNumber("Test");
    }

    @Test
    void FindAllTest_ReturnTrue() throws ProductInvalidParameterException {
        Pageable pageable = PageRequest.of(0,1);

        Page<Product>products=Page.empty(pageable);
        when(productRepository.findAll(pageable)).thenReturn(products);

        Page<Product> productList = productService.findAll(0, 1);

        Assert.assertEquals(0,productList.getContent().size());

        verify(productRepository,times(1)).findAll(pageable);
    }

    @Test
    void ExistsBySerialNumberTest_ReturnTrue() throws ProductInvalidParameterException {
        Product productBySerialNumber = new Product();
        productBySerialNumber.setId(123L);
        productBySerialNumber.setName("Test");
        productBySerialNumber.setDescription("Test");
        productBySerialNumber.setSerialNumber("Test");

        when(productRepository.existsBySerialNumber("Test")).thenReturn(true);

        boolean existsBySerialNumber = productService.existsBySerialNumber("Test");

        Assert.assertTrue(existsBySerialNumber);
    }

}