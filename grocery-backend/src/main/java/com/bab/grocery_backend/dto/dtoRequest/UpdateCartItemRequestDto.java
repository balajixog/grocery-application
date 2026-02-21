package com.bab.grocery_backend.dto.dtoRequest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequestDto {

    @NotNull
    private Long productId;

    @NotNull
    @Min(1)
    private Integer quantity;
}