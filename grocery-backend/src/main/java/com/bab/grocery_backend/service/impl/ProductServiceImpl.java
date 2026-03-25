package com.bab.grocery_backend.service.impl;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bab.grocery_backend.dto.dtoRequest.CreateProductRequestDto;
import com.bab.grocery_backend.dto.dtoRequest.UpdateStockRequestDto;
import com.bab.grocery_backend.dto.dtoResponse.ProductResponseDto;
import com.bab.grocery_backend.entity.Category;
import com.bab.grocery_backend.entity.Product;
import com.bab.grocery_backend.repository.CategoryRepository;
import com.bab.grocery_backend.repository.ProductRepository;
import com.bab.grocery_backend.service.ProductService;

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
                saved.getStockQuantity() > 0,
                product.getImageUrl()
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
                        product.getStockQuantity() > 0,
                        product.getImageUrl()
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
                    product.getStockQuantity() > 0,
                    product.getImageUrl()
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
                        product.getStockQuantity() > 0,
                        product.getImageUrl()
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
                saved.getStockQuantity() > 0,
                product.getImageUrl()
        );
        }
        @Override
        public Page<ProductResponseDto> getFilteredProducts(
                String search,
                Long categoryId,
                int page,
                int size) {
        
            Pageable pageable = PageRequest.of(page, size);
        
            Page<Product> products;
        
            if (search != null && categoryId != null) {
        
                products = productRepository
                        .findByNameContainingIgnoreCaseAndCategoryId(search, categoryId, pageable);
        
            } 
            else if (search != null) {
        
                products = productRepository
                        .findByNameContainingIgnoreCase(search, pageable);
        
            } 
            else if (categoryId != null) {
        
                products = productRepository
                        .findByCategoryId(categoryId, pageable);
        
            } 
            else {
        
                products = productRepository.findAll(pageable);
        
            }
        
            return products.map(this::mapToDto);  // product -> mapToDto(product)
        }
        private ProductResponseDto mapToDto(Product product) {

                return new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getCategory().getName(),
                        product.getStockQuantity() > 0,
                        product.getImageUrl()
                );
            }
        
            @Override
                public void updateImage(Long productId, String imageUrl) {

                Product product = productRepository.findById(productId)
                        .orElseThrow(() -> new RuntimeException("Product not found"));

                product.setImageUrl(imageUrl);

                productRepository.save(product);
        }
        @Override
        public ProductResponseDto updateProduct(Long id, CreateProductRequestDto dto) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(category); 

        productRepository.save(product);

        return mapToDto(product);
        }

        @Override
        public void deleteProduct(Long productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);
        }
        

}
