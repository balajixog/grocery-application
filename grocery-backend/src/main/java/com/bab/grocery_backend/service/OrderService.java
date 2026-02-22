package com.bab.grocery_backend.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.bab.grocery_backend.dto.dtoRequest.AdminOrderResponseDto;
import com.bab.grocery_backend.dto.dtoRequest.OrderHistoryResponseDto;
import com.bab.grocery_backend.dto.dtoResponse.OrderDetailsResponseDto;
import com.bab.grocery_backend.dto.dtoResponse.OrderTrackingResponseDto;

public interface OrderService {
    void placeOrder(String userEmail);
    List<OrderHistoryResponseDto> getOrderHistory(String userEmail);
    Page<AdminOrderResponseDto> getAllOrders(int page, int size, String sortBy, String direction);
    void updateOrderStatus(Long orderId, String status);
    void cancelOrder(Long orderId);
    OrderDetailsResponseDto getOrderDetails(Long orderId);
    OrderTrackingResponseDto trackOrder(Long orderId);
}
