package com.bab.grocery_backend.service;

import java.util.List;

import com.bab.grocery_backend.dto.CreateProductRequestDto;
import com.bab.grocery_backend.dto.ProductResponseDto;

public interface ProductService {
    ProductResponseDto createProduct(CreateProductRequestDto dto);
    public List<ProductResponseDto> getAllProducts();
}
