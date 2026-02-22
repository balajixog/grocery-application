package com.bab.grocery_backend.dto.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AdminOrderResponseDto {
    private Long orderId;
    private String userEmail;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;
}