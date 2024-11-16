package com.youtube.jwt.service;

import com.youtube.jwt.entity.ImageModel;
import com.youtube.jwt.entity.Product;
import com.youtube.jwt.repository.ImageRepository;
import com.youtube.jwt.repository.ProductRepository;
import com.youtube.jwt.utils.ImageUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.exception.ContextedRuntimeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.zip.DataFormatException;

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

//    images upload
public Product createProductWithImages(Product product, List<MultipartFile> imageFiles) throws IOException {
    Set<ImageModel> images = new HashSet<>();

    try {
        for (MultipartFile imageFile : imageFiles) {
            ImageModel image = ImageModel.builder()
                    .name(imageFile.getOriginalFilename())
                    .type(imageFile.getContentType())
                    .imageData(ImageUtils.compressImage(imageFile.getBytes()))
                    .build();
            images.add(image);
        }

        product.setProductImages(images);
        return productRepository.save(product);

    } catch (IOException e) {
        throw new RuntimeException("Error compressing or saving images", e);
    }
}

    public void deleteProductById(Integer productId) {
        productRepository.deleteById(productId);
    }
}
