package com.bab.grocery_backend.service;

import com.bab.grocery_backend.dto.CategoryResponseDto;
import com.bab.grocery_backend.dto.CreateCategoryRequestDto;

public interface CategoryService {
    CategoryResponseDto createCategory(CreateCategoryRequestDto dto);
}
