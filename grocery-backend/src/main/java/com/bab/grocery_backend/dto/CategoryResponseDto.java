package com.bab.grocery_backend.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class CategoryResponseDto {
    private Long id;
    private String name;
}