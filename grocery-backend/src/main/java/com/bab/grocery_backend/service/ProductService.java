package com.bab.grocery_backend.service;


import org.springframework.data.domain.Page;

import com.bab.grocery_backend.dto.dtoRequest.CreateProductRequestDto;
import com.bab.grocery_backend.dto.dtoRequest.UpdateStockRequestDto;
import com.bab.grocery_backend.dto.dtoResponse.ProductResponseDto;

public interface ProductService {
    ProductResponseDto createProduct(CreateProductRequestDto dto);
    Page<ProductResponseDto> getAllProducts(int page, int size);
    Page<ProductResponseDto> getProductsByCategory(Long categoryId, int page, int size);
    Page<ProductResponseDto> searchProducts(String keyword, int page, int size);
    ProductResponseDto updateStock(Long productId, UpdateStockRequestDto dto);


}
