package com.bab.grocery_backend.service;

import com.bab.grocery_backend.dto.AddToCartRequestDto;

public interface CartService {
    void addToCart(AddToCartRequestDto dto, String userEmail);
}