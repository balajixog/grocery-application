package com.bab.grocery_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderTrackingStepDto {
    private String step;
    private boolean completed;
}