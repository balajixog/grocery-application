package com.bab.grocery_backend.service;


import org.springframework.data.domain.Page;

import com.bab.grocery_backend.dto.CreateProductRequestDto;
import com.bab.grocery_backend.dto.ProductResponseDto;
import com.bab.grocery_backend.dto.UpdateStockRequestDto;

public interface ProductService {
    ProductResponseDto createProduct(CreateProductRequestDto dto);
    Page<ProductResponseDto> getAllProducts(int page, int size);
    Page<ProductResponseDto> getProductsByCategory(Long categoryId, int page, int size);
    Page<ProductResponseDto> searchProducts(String keyword, int page, int size);
    ProductResponseDto updateStock(Long productId, UpdateStockRequestDto dto);


}
