package com.bab.grocery_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bab.grocery_backend.dto.dtoRequest.AdminOrderResponseDto;
import com.bab.grocery_backend.dto.dtoResponse.OrderDetailsResponseDto;
import com.bab.grocery_backend.service.OrderService;

@RestController
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;

    //  View all orders
    @GetMapping
    public ResponseEntity<Page<AdminOrderResponseDto>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        return ResponseEntity.ok(
                orderService.getAllOrders(page, size, sortBy, direction)
        );
    }

    //  Update order status
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok("Order status updated");
    }
    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {

        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled and stock restored");
    }
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsResponseDto> getOrderDetails(
            @PathVariable Long orderId) {

        return ResponseEntity.ok(orderService.getOrderDetails(orderId));
    }
}