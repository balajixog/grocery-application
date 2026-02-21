package com.bab.grocery_backend.dto.dtoRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCategoryRequestDto {
    @NotBlank
    private String name;
}