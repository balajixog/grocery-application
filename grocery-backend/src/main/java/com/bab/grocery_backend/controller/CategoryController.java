package com.bab.grocery_backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.bab.grocery_backend.dto.dtoRequest.CreateCategoryRequestDto;
import com.bab.grocery_backend.dto.dtoResponse.CategoryResponseDto;
import com.bab.grocery_backend.service.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;


    @PostMapping("/admin/categories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryResponseDto> createCategory(
            @Valid @RequestBody CreateCategoryRequestDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(dto));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}