package com.example.product_api;

import com.example.product_api.models.Product;
import com.example.product_api.repository.ProductRepository;
import com.example.product_api.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveProduct() {
        // Arrange
        Product product = new Product("Test Product", 99.99);

        // Simulate the saved product with an assigned id
        Product savedProduct = new Product("Test Product", 99.99);
        savedProduct.setId("1"); // Set the id manually

        when(productRepository.save(product)).thenReturn(savedProduct);

        // Act
        Product result = productService.saveProduct(product);

        // Assert
        assertNotNull(result.getId());
        assertEquals("Test Product", result.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testGetAllProducts() {
        // Arrange
        Product product1 = new Product("Product 1", 10.0);
        product1.setId("1");
        Product product2 = new Product("Product 2", 20.0);
        product2.setId("2");

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        // Act
        List<Product> products = productService.getAllProducts();

        // Assert
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void testGetProductById() {
        // Arrange
        String productId = "1";
        Product product = new Product("Test Product", 99.99);
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> result = productService.getProductById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Test Product", result.get().getName());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void testDeleteProduct() {
        // Arrange
        String productId = "1";

        doNothing().when(productRepository).deleteById(productId);

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).deleteById(productId);
    }
}