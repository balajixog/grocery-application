package com.bab.grocery_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bab.grocery_backend.dto.dtoRequest.AddToCartRequestDto;
import com.bab.grocery_backend.dto.dtoRequest.UpdateCartItemRequestDto;
import com.bab.grocery_backend.dto.dtoResponse.CartResponseDto;
import com.bab.grocery_backend.service.CartService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("user/cart")
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

    @GetMapping
    public ResponseEntity<CartResponseDto> viewCart(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(cartService.viewCart(email));
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateCartItem(
            @Valid @RequestBody UpdateCartItemRequestDto dto,
            Authentication authentication) {

        String email = authentication.getName();
        cartService.updateCartItem(dto, email);

        return ResponseEntity.ok("Cart item updated");
    }
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<?> removeCartItem(
            @PathVariable Long productId,
            Authentication authentication) {

        String email = authentication.getName();
        cartService.removeCartItem(productId, email);

        return ResponseEntity.ok("Item removed from cart");
    }  
}