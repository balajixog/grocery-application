package com.bab.grocery_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bab.grocery_backend.dto.dtoRequest.OrderHistoryResponseDto;
import com.bab.grocery_backend.dto.dtoRequest.PlaceOrderRequestDto;
import com.bab.grocery_backend.dto.dtoResponse.OrderTrackingResponseDto;
import com.bab.grocery_backend.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("user/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<?> placeOrder(
            Authentication authentication,
            @RequestBody PlaceOrderRequestDto dto) {

        String email = authentication.getName();
        orderService.placeOrder(email, dto.getAddressId());

        return ResponseEntity.ok("Order placed successfully");
    }
    @GetMapping("/history")
    public ResponseEntity<List<OrderHistoryResponseDto>> getOrderHistory(
            Authentication authentication) {

        String email = authentication.getName();
        return ResponseEntity.ok(orderService.getOrderHistory(email));
    }
    @GetMapping("/{orderId}/track")
    public ResponseEntity<OrderTrackingResponseDto> trackOrder(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.trackOrder(orderId));
    }
    
}