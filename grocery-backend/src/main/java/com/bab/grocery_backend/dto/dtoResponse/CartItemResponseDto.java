package com.bab.grocery_backend.dto.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CartItemResponseDto {

    private Long productId;
    private String name;
    private Double price;
    private Integer quantity;
    private Double totalPrice;
}