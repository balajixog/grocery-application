package com.bab.grocery_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import com.bab.grocery_backend.dto.CreateProductRequestDto;
import com.bab.grocery_backend.dto.ProductResponseDto;
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
        public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
            return ResponseEntity.ok(productService.getAllProducts());
}
}
