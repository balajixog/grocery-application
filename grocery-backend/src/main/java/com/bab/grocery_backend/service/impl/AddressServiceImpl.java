package com.bab.grocery_backend.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bab.grocery_backend.dto.dtoRequest.CreateAddressRequestDto;
import com.bab.grocery_backend.dto.dtoResponse.AddressResponseDto;
import com.bab.grocery_backend.entity.Address;
import com.bab.grocery_backend.entity.User;
import com.bab.grocery_backend.repository.AddressRepository;
import com.bab.grocery_backend.repository.UserRepository;
import com.bab.grocery_backend.service.AddressService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public List<AddressResponseDto> getUserAddresses(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return addressRepository.findByUser(user)
                .stream()
                .map(addr -> new AddressResponseDto(
                        addr.getId(),
                        addr.getLine1(),
                        addr.getCity(),
                        addr.getState(),
                        addr.getPincode()
                ))
                .toList();
    }

    @Override
    public AddressResponseDto addAddress(String email, CreateAddressRequestDto dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address address = Address.builder()
                .line1(dto.getLine1())
                .city(dto.getCity())
                .state(dto.getState())
                .pincode(dto.getPincode())
                .user(user)
                .build();

        Address saved = addressRepository.save(address);

        return new AddressResponseDto(
                saved.getId(),
                saved.getLine1(),
                saved.getCity(),
                saved.getState(),
                saved.getPincode()
        );
    }

    @Override
    public void deleteAddress(Long addressId, String email) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Unauthorized");
        }

        addressRepository.delete(address);
    }
}