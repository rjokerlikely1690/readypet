package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    List<Product> findByActiveTrue();
    
    List<Product> findByCategoryAndActiveTrue(String category);
    
    List<Product> findByNameContainingIgnoreCaseAndActiveTrue(String name);
    
    List<Product> findByPriceBetweenAndActiveTrue(BigDecimal minPrice, BigDecimal maxPrice);
    
    List<Product> findByStockLessThanAndActiveTrue(Integer stockThreshold);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.stock < :threshold")
    List<Product> findLowStockProducts(@Param("threshold") Integer threshold);
    
    @Query("SELECT p FROM Product p WHERE p.active = true AND p.category = :category")
    List<Product> findProductsByCategory(@Param("category") String category);
} 