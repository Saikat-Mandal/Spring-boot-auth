package com.youtube.jwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtube.jwt.entity.ImageModel;
import com.youtube.jwt.entity.Product;
import com.youtube.jwt.service.ProductService;
import com.youtube.jwt.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final ObjectMapper objectMapper;

    // Create a new product
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/product", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Product> createProduct(
            @RequestPart("product") String productString,
            @RequestPart("images") List<MultipartFile> imageFiles) {

        try {
            // Convert JSON string of Product to Product object
            Product product = objectMapper.readValue(productString, Product.class);
            System.out.println(product.getName());
            // Call service to save product with images
            Product savedProduct = productService.createProductWithImages(product, imageFiles);
            System.out.println(savedProduct.getName());

            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a product by id
    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {
        Product gotProduct = productService.getProductById(productId);
        return new ResponseEntity<>(gotProduct, HttpStatus.OK);
    }

    // Get all products
    @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //delete product
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable Integer productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

