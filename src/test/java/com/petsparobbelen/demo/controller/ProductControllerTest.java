package com.petsparobbelen.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petsparobbelen.demo.model.Product;
import com.petsparobbelen.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas unitarias para el controlador de productos
 * 
 * Verifica el comportamiento correcto de los endpoints REST
 * para la gestión de productos.
 */
@WebMvcTest(ProductController.class)
class ProductControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private ProductService productService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private Product testProduct;
    
    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1L);
        testProduct.setName("Laptop HP Pavilion");
        testProduct.setDescription("Laptop de 15 pulgadas con procesador Intel i5");
        testProduct.setPrice(new BigDecimal("899.99"));
        testProduct.setStock(25);
        testProduct.setCategory("Electrónicos");
        testProduct.setCreatedAt(LocalDateTime.now());
        testProduct.setUpdatedAt(LocalDateTime.now());
        testProduct.setActive(true);
    }
    
    @Test
    void getAllProducts_ShouldReturnProductsList() throws Exception {
        // Arrange
        when(productService.getAllActiveProducts())
                .thenReturn(Arrays.asList(testProduct));
        
        // Act & Assert
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._embedded.productList[0].name").value("Laptop HP Pavilion"))
                .andExpect(jsonPath("$._embedded.productList[0].category").value("Electrónicos"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.create.href").exists());
    }
    
    @Test
    void getProductById_WithValidId_ShouldReturnProduct() throws Exception {
        // Arrange
        when(productService.getProductById(1L))
                .thenReturn(Optional.of(testProduct));
        
        // Act & Assert
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Laptop HP Pavilion"))
                .andExpect(jsonPath("$.category").value("Electrónicos"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.products.href").exists());
    }
    
    @Test
    void getProductById_WithInvalidId_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(productService.getProductById(999L))
                .thenReturn(Optional.empty());
        
        // Act & Assert
        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void createProduct_WithValidData_ShouldReturnCreatedProduct() throws Exception {
        // Arrange
        when(productService.createProduct(any(Product.class)))
                .thenReturn(testProduct);
        
        // Act & Assert
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Laptop HP Pavilion"))
                .andExpect(jsonPath("$.category").value("Electrónicos"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.products.href").exists());
    }
    
    @Test
    void createProduct_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        // Arrange
        Product invalidProduct = new Product();
        invalidProduct.setName(""); // Nombre vacío
        
        // Act & Assert
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void updateProduct_WithValidId_ShouldReturnUpdatedProduct() throws Exception {
        // Arrange
        when(productService.updateProduct(1L, any(Product.class)))
                .thenReturn(testProduct);
        
        // Act & Assert
        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Laptop HP Pavilion"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.products.href").exists());
    }
    
    @Test
    void updateProduct_WithInvalidId_ShouldReturnNotFound() throws Exception {
        // Arrange
        when(productService.updateProduct(999L, any(Product.class)))
                .thenThrow(new RuntimeException("Producto no encontrado"));
        
        // Act & Assert
        mockMvc.perform(put("/api/products/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProduct)))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void deleteProduct_WithValidId_ShouldReturnSuccessMessage() throws Exception {
        // Arrange
        // No necesitamos mockear nada para delete ya que no retorna nada
        
        // Act & Assert
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.mensaje").value("Producto eliminado exitosamente"))
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$._links.products.href").exists());
    }
    
    @Test
    void getProductsByCategory_ShouldReturnFilteredProducts() throws Exception {
        // Arrange
        when(productService.getProductsByCategory("Electrónicos"))
                .thenReturn(Arrays.asList(testProduct));
        
        // Act & Assert
        mockMvc.perform(get("/api/products/category/Electrónicos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._embedded.productList[0].category").value("Electrónicos"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.products.href").exists());
    }
    
    @Test
    void searchProductsByName_ShouldReturnMatchingProducts() throws Exception {
        // Arrange
        when(productService.searchProductsByName("Laptop"))
                .thenReturn(Arrays.asList(testProduct));
        
        // Act & Assert
        mockMvc.perform(get("/api/products/search")
                        .param("name", "Laptop"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$._embedded.productList[0].name").value("Laptop HP Pavilion"))
                .andExpect(jsonPath("$._links.self.href").exists())
                .andExpect(jsonPath("$._links.products.href").exists());
    }
    
    @Test
    void updateStock_WithValidData_ShouldReturnUpdatedProduct() throws Exception {
        // Arrange
        when(productService.updateStock(1L, 30))
                .thenReturn(testProduct);
        
        // Act & Assert
        mockMvc.perform(patch("/api/products/1/stock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"stock\": 30}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Laptop HP Pavilion"))
                .andExpect(jsonPath("$._links.self.href").exists());
    }
} 