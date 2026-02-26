package com.bab.grocery_backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.bab.grocery_backend.dto.dtoRequest.CreateAddressRequestDto;
import com.bab.grocery_backend.dto.dtoResponse.AddressResponseDto;
import com.bab.grocery_backend.service.AddressService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    //  Get all addresses of logged-in user
    @GetMapping
    public ResponseEntity<List<AddressResponseDto>> getUserAddresses(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(addressService.getUserAddresses(email));
    }

    //  Add new address
    @PostMapping
    public ResponseEntity<AddressResponseDto> addAddress(
            Authentication authentication,
            @RequestBody CreateAddressRequestDto dto) {

        String email = authentication.getName();
        return ResponseEntity.ok(addressService.addAddress(email, dto));
    }

    //  Delete address (only if belongs to that user)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAddress(
            @PathVariable Long id,
            Authentication authentication) {

        String email = authentication.getName();
        addressService.deleteAddress(id, email);
        return ResponseEntity.ok("Address deleted successfully");
    }
}