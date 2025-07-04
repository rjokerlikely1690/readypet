package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getAllProducts() {
        List<EntityModel<Product>> products = productService.getAllProducts().stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Product>> collectionModel = CollectionModel.of(products);
        collectionModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        EntityModel<Product> entityModel = EntityModel.of(product);
        entityModel.add(linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @PostMapping
    public ResponseEntity<EntityModel<Product>> createProduct(@Valid @RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        
        EntityModel<Product> entityModel = EntityModel.of(createdProduct);
        entityModel.add(linkTo(methodOn(ProductController.class).getProductById(createdProduct.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Product>> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        
        EntityModel<Product> entityModel = EntityModel.of(updatedProduct);
        entityModel.add(linkTo(methodOn(ProductController.class).getProductById(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
        
        return ResponseEntity.ok(entityModel);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getProductsByCategory(@PathVariable String category) {
        List<EntityModel<Product>> products = productService.getProductsByCategory(category).stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Product>> collectionModel = CollectionModel.of(products);
        collectionModel.add(linkTo(methodOn(ProductController.class).getProductsByCategory(category)).withSelfRel());
        collectionModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @GetMapping("/search")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> searchProducts(@RequestParam String name) {
        List<EntityModel<Product>> products = productService.searchProductsByName(name).stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Product>> collectionModel = CollectionModel.of(products);
        collectionModel.add(linkTo(methodOn(ProductController.class).searchProducts(name)).withSelfRel());
        collectionModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @GetMapping("/price-range")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getProductsByPriceRange(
            @RequestParam BigDecimal minPrice, @RequestParam BigDecimal maxPrice) {
        List<EntityModel<Product>> products = productService.getProductsByPriceRange(minPrice, maxPrice).stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Product>> collectionModel = CollectionModel.of(products);
        collectionModel.add(linkTo(methodOn(ProductController.class).getProductsByPriceRange(minPrice, maxPrice)).withSelfRel());
        collectionModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
        
        return ResponseEntity.ok(collectionModel);
    }
    
    @GetMapping("/low-stock")
    public ResponseEntity<CollectionModel<EntityModel<Product>>> getLowStockProducts(@RequestParam(defaultValue = "10") Integer threshold) {
        List<EntityModel<Product>> products = productService.getLowStockProducts(threshold).stream()
                .map(product -> EntityModel.of(product,
                        linkTo(methodOn(ProductController.class).getProductById(product.getId())).withSelfRel(),
                        linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products")))
                .collect(Collectors.toList());
        
        CollectionModel<EntityModel<Product>> collectionModel = CollectionModel.of(products);
        collectionModel.add(linkTo(methodOn(ProductController.class).getLowStockProducts(threshold)).withSelfRel());
        collectionModel.add(linkTo(methodOn(ProductController.class).getAllProducts()).withRel("products"));
        
        return ResponseEntity.ok(collectionModel);
    }
} 