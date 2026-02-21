package com.bab.grocery_backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import com.bab.grocery_backend.dto.dtoRequest.CreateProductRequestDto;
import com.bab.grocery_backend.dto.dtoRequest.UpdateStockRequestDto;
import com.bab.grocery_backend.dto.dtoResponse.ProductResponseDto;
import com.bab.grocery_backend.service.ProductService;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> createProduct(
            @Valid @RequestBody CreateProductRequestDto dto) {

        ProductResponseDto response = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
        @GetMapping
        public ResponseEntity<Page<ProductResponseDto>> getAllProducts(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "5") int size) {

            return ResponseEntity.ok(productService.getAllProducts(page, size));
        }
        @GetMapping("/category/{categoryId}")
        public ResponseEntity<Page<ProductResponseDto>> getProductsByCategory(
        @PathVariable Long categoryId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(
                productService.getProductsByCategory(categoryId, page, size)
        );
    }
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDto>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(
                productService.searchProducts(keyword, page, size)
        );
    }
    @PatchMapping("/{productId}/stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> updateStock(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateStockRequestDto dto) {

        return ResponseEntity.ok(
                productService.updateStock(productId, dto)
        );
    }




}
