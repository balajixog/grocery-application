package com.bab.grocery_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.bab.grocery_backend.dto.CreateProductRequestDto;
import com.bab.grocery_backend.dto.ProductResponseDto;
import com.bab.grocery_backend.entity.Category;
import com.bab.grocery_backend.entity.Product;
import com.bab.grocery_backend.repository.CategoryRepository;
import com.bab.grocery_backend.repository.ProductRepository;

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
                saved.getCategory().getName()
        );
    }
        @Override
        public List<ProductResponseDto> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getCategory().getName()
                ))
                .toList();
        }
}
