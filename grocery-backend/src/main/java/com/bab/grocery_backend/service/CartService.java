package com.bab.grocery_backend.service;

import com.bab.grocery_backend.dto.CartResponseDto;
import com.bab.grocery_backend.dto.dtoRequest.AddToCartRequestDto;
import com.bab.grocery_backend.dto.dtoRequest.UpdateCartItemRequestDto;

public interface CartService {
    void addToCart(AddToCartRequestDto dto, String userEmail);
    CartResponseDto viewCart(String userEmail);
    void updateCartItem(UpdateCartItemRequestDto dto, String userEmail);
    void removeCartItem(Long productId, String userEmail);
}