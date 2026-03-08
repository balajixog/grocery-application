package com.bab.grocery_backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bab.grocery_backend.dto.dtoResponse.ProductResponseDto;
import com.bab.grocery_backend.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class UserProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getProducts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {

        return ResponseEntity.ok(
                productService.getFilteredProducts(search, categoryId, page, size)
        );
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<ProductResponseDto>> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {

        return ResponseEntity.ok(
                productService.getProductsByCategory(categoryId, page, size)
        );
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDto>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size) {

        return ResponseEntity.ok(
                productService.searchProducts(keyword, page, size)
        );
    }
}