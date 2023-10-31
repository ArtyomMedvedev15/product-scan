package com.productscan.repository;

import com.productscan.entity.Product;
import org.junit.Assert;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest
@Testcontainers
@Sql(value = "classpath:/sql/clearData.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "classpath:/sql/clearData.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ProductRepositoryTest {

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

    @Autowired
    private ProductRepository productRepository;



    @Test
    public void SaveProductTest_ReturnTrue(){
        Product productSave = new Product();
        productSave.setName("Test");
        productSave.setDescription("Test");
        productSave.setSerialNumber("Test");
        productSave.setPhotoUrl("Test");

        Product productSaveResult = productRepository.save(productSave);
        Assert.assertEquals("Test", productSaveResult.getName());
    }

    @Test
    public void GetProductByIdTest_ReturnTrue(){
        Product productSave = new Product();
        productSave.setName("Test");
        productSave.setDescription("Test");
        productSave.setSerialNumber("Test");
        productSave.setPhotoUrl("Test");

        Product productSaveResult = productRepository.save(productSave);

        Product productById = productRepository.findById(productSaveResult.getId()).get();
        Assert.assertEquals("Test", productById.getName());
    }

    @Test
    public void FindBySerialNumberTest_ReturnTrue(){
        Product productSave = new Product();
        productSave.setName("Test");
        productSave.setDescription("Test");
        productSave.setSerialNumber("Test");
        productSave.setPhotoUrl("Test");

        Product productSaveResult = productRepository.save(productSave);

        Product productById = productRepository.findBySerialNumber(productSaveResult.getSerialNumber());
        Assert.assertEquals("Test", productById.getSerialNumber());
    }

    @Test
    public void ExistedProductBySerialNumberTest_ReturnTrue(){
        Product productSave = new Product();
        productSave.setName("Test");
        productSave.setDescription("Test");
        productSave.setSerialNumber("Test");
        productSave.setPhotoUrl("Test");
        Product productSaveResult = productRepository.save(productSave);
        boolean existsBySerialNumber = productRepository.existsBySerialNumber(productSaveResult.getSerialNumber());
        Assert.assertTrue(existsBySerialNumber);
    }

    @Test
    public void DeleteProductByIdTest_ReturnTrue(){
        Product productSave = new Product();
        productSave.setName("Test");
        productSave.setDescription("Test");
        productSave.setSerialNumber("Test");
        productSave.setPhotoUrl("Test");

        Product productSaveResult = productRepository.save(productSave);
        productRepository.delete(productSaveResult);
        Optional<Product> deletedProduct = productRepository.findById(productSaveResult.getId());
        Assert.assertFalse(deletedProduct.isPresent());
    }

    @Test
    public void GetAllProductTest_ReturnTrue(){
        Product productSave = new Product();
        productSave.setName("Test");
        productSave.setDescription("Test");
        productSave.setSerialNumber("Test");
        productSave.setPhotoUrl("Test");

        Product productSaveResult = productRepository.save(productSave);

        Pageable pageable = PageRequest.of(0, 1);
        List<Product> productList = productRepository.findAll(pageable).getContent();
        Assert.assertTrue(productList.contains(productSaveResult));
    }

    @Test
    public void UpdateroductTest_ReturnTrue(){
        Product productSave = new Product();
        productSave.setName("Test");
        productSave.setDescription("Test");
        productSave.setSerialNumber("Test");
        productSave.setPhotoUrl("Test");

        Product productSaveResult = productRepository.save(productSave);

        productSaveResult.setName("Updated");
        Product updatedProduct = productRepository.save(productSaveResult);

        Assert.assertEquals("Updated", updatedProduct.getName());
        Assert.assertEquals(productSaveResult.getId(),updatedProduct.getId());
    }

}