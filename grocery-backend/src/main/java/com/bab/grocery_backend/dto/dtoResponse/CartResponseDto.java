package com.bab.grocery_backend.dto.dtoResponse;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartResponseDto {

    private List<CartItemResponseDto> items;
    private Double cartTotal;
}