package com.bab.grocery_backend.service;

import com.bab.grocery_backend.dto.dtoRequest.CreateCategoryRequestDto;
import com.bab.grocery_backend.dto.dtoResponse.CategoryResponseDto;

public interface CategoryService {
    CategoryResponseDto createCategory(CreateCategoryRequestDto dto);
}
