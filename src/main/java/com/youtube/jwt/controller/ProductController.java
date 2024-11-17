package com.youtube.jwt.controller;

import com.youtube.jwt.entity.Category;
import com.youtube.jwt.entity.ImageModel;
import com.youtube.jwt.entity.Product;
import com.youtube.jwt.repository.CategoryRepository;
import com.youtube.jwt.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;

    // Create a new product
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/product", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Product> createProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("brand") String brand,
            @RequestParam("quantity") int quantity,
            @RequestParam("discountedPrice") double discountedPrice,
            @RequestParam("actualPrice") double actualPrice,
            @RequestParam("category") String categoryName,
            @RequestParam("imageUrls") List<String> imageUrls) {

        try {

            Category category = categoryRepository.findByName(categoryName);
            if(category == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setBrand(brand);
            product.setQuantity(quantity);
            product.setDiscountedPrice(discountedPrice);
            product.setActualPrice(actualPrice);
            product.setCategory(category);

            List<ImageModel> imageModels = new ArrayList<>();
            for (String imageUrl : imageUrls) {
                ImageModel imageModel = new ImageModel();
                imageModel.setImageUrl(imageUrl);
                imageModel.setProduct(product);
                imageModels.add(imageModel);
            }
            product.setProductImages(imageModels);

            Product savedProduct = productService.createProduct(product);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get a product by ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // Get all products
    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
