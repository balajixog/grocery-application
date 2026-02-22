package com.bab.grocery_backend.dto.dtoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderHistoryResponseDto {

    private Long orderId;
    private Double totalAmount;
    private String status;
    private LocalDateTime createdAt;
}