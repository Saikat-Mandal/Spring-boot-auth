package com.youtube.jwt.service;

import com.youtube.jwt.entity.Product;
import com.youtube.jwt.repository.ImageRepository;
import com.youtube.jwt.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

//    create a new product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

//    get a product by id
    public Product getProductById(Integer productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
    }

//    get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

//    delete a product
    public void deleteProductById(Integer productId) {
        productRepository.deleteById(productId);
    }
}
