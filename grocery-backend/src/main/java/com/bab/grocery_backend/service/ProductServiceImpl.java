package com.bab.grocery_backend.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bab.grocery_backend.dto.CreateProductRequestDto;
import com.bab.grocery_backend.dto.ProductResponseDto;
import com.bab.grocery_backend.dto.UpdateStockRequestDto;
import com.bab.grocery_backend.entity.Category;
import com.bab.grocery_backend.entity.Product;
import com.bab.grocery_backend.repository.CategoryRepository;
import com.bab.grocery_backend.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponseDto createProduct(CreateProductRequestDto dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .category(category)
                .build();

        Product saved = productRepository.save(product);

        return new ProductResponseDto(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getStockQuantity(),
                saved.getCategory().getName(),
                saved.getStockQuantity() > 0
        );
    }
        @Override
        public Page<ProductResponseDto> getAllProducts(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return productRepository.findAll(pageable)
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getCategory().getName(),
                        product.getStockQuantity() > 0
                ));
        }
        @Override
        public Page<ProductResponseDto> getProductsByCategory(Long categoryId, int page, int size) {

         Pageable pageable = PageRequest.of(page, size);

         return productRepository.findByCategoryId(categoryId, pageable)
            .map(product -> new ProductResponseDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getStockQuantity(),
                    product.getCategory().getName(),
                    product.getStockQuantity() > 0
            ));
        }
        @Override
        public Page<ProductResponseDto> searchProducts(String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        return productRepository
                .findByNameContainingIgnoreCase(keyword, pageable)
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getCategory().getName(),
                        product.getStockQuantity() > 0
                ));
        }

        @Override
        public ProductResponseDto updateStock(Long productId, UpdateStockRequestDto dto) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setStockQuantity(dto.getStockQuantity());

        Product saved = productRepository.save(product);

        return new ProductResponseDto(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getStockQuantity(),
                saved.getCategory().getName(),
                saved.getStockQuantity() > 0
        );
        }




}
