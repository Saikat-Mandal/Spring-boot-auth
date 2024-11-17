package com.youtube.jwt.service;

import com.youtube.jwt.entity.Category;
import com.youtube.jwt.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
       return categoryRepository.findAll();
    }

}
