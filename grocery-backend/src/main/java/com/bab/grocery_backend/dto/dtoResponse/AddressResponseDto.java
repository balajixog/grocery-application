package com.bab.grocery_backend.dto.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressResponseDto {
    private Long id;
    private String line1;
    private String city;
    private String state;
    private String pincode;
}