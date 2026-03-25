package com.bab.grocery_backend.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import com.bab.grocery_backend.dto.dtoRequest.CreateProductRequestDto;
import com.bab.grocery_backend.dto.dtoRequest.UpdateStockRequestDto;
import com.bab.grocery_backend.dto.dtoResponse.ProductResponseDto;
import com.bab.grocery_backend.service.ImageService;
import com.bab.grocery_backend.service.ProductService;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // ✅ ONLY THIS IS ENOUGH
public class ProductController {

    private final ProductService productService;
    private final ImageService imageService;

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(
            @Valid @RequestBody CreateProductRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.createProduct(dto));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @PatchMapping("/{productId}/stock")
    public ResponseEntity<ProductResponseDto> updateStock(
            @PathVariable Long productId,
            @Valid @RequestBody UpdateStockRequestDto dto) {

        return ResponseEntity.ok(productService.updateStock(productId, dto));
    }

    @PostMapping("/{id}/image")
    public ResponseEntity<?> uploadImage(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file) {

        String url = imageService.uploadImage(file);

        productService.updateImage(id, url);

        return ResponseEntity.ok(url);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable Long productId,
            @RequestBody CreateProductRequestDto dto) {

        return ResponseEntity.ok(
                productService.updateProduct(productId, dto)
        );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {

        productService.deleteProduct(productId);

        return ResponseEntity.ok("Product deleted");
    }
}