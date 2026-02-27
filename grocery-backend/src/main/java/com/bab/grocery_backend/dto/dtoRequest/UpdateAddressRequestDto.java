package com.bab.grocery_backend.dto.dtoRequest;

import lombok.Data;

@Data
public class UpdateAddressRequestDto {
    private String line1;
    private String city;
    private String state;
    private String pincode;
}