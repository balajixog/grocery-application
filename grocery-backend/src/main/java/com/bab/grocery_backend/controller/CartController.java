package com.bab.grocery_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import com.bab.grocery_backend.dto.AddToCartRequestDto;
import com.bab.grocery_backend.service.CartService;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(
            @Valid @RequestBody AddToCartRequestDto dto,
            Authentication authentication) {

        String email = authentication.getName();
        cartService.addToCart(dto, email);

        return ResponseEntity.ok("Product added to cart");
    }
}