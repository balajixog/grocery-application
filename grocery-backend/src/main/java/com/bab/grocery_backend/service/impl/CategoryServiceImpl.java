package com.bab.grocery_backend.service.impl;

import org.springframework.stereotype.Service;

import com.bab.grocery_backend.dto.CategoryResponseDto;
import com.bab.grocery_backend.dto.CreateCategoryRequestDto;
import com.bab.grocery_backend.entity.Category;
import com.bab.grocery_backend.repository.CategoryRepository;
import com.bab.grocery_backend.service.CategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
public CategoryResponseDto createCategory(CreateCategoryRequestDto dto) {

    if (categoryRepository.existsByName(dto.getName())) {
        throw new RuntimeException("Category already exists");
    }

    Category category = Category.builder()   
            .name(dto.getName())
            .build();

    Category savedCategory = categoryRepository.save(category);

    return new CategoryResponseDto(
            savedCategory.getId(),
            savedCategory.getName()
    );
}
    
}
