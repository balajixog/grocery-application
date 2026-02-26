package com.bab.grocery_backend.service;

import java.util.List;

import com.bab.grocery_backend.dto.dtoRequest.CreateAddressRequestDto;
import com.bab.grocery_backend.dto.dtoResponse.AddressResponseDto;

public interface AddressService {
    List<AddressResponseDto> getUserAddresses(String email);
    AddressResponseDto addAddress(String email, CreateAddressRequestDto dto);
    void deleteAddress(Long addressId, String email);
}
