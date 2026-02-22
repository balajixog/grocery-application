package com.bab.grocery_backend.dto.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import com.bab.grocery_backend.dto.OrderTrackingStepDto;

@Data
@AllArgsConstructor
public class OrderTrackingResponseDto {
    private Long orderId;
    private String currentStatus;
    private List<OrderTrackingStepDto> timeline;
}