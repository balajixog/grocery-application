package com.bab.grocery_backend.service.impl;

import org.springframework.stereotype.Service;

import com.bab.grocery_backend.dto.dtoRequest.UpdateProfileRequestDto;
import com.bab.grocery_backend.dto.dtoRequest.UserProfileResponseDto;
import com.bab.grocery_backend.entity.User;
import com.bab.grocery_backend.repository.UserRepository;
import com.bab.grocery_backend.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
    @Override
    public UserProfileResponseDto getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileResponseDto(user.getEmail());
    }

    @Override
    public UserProfileResponseDto updateProfile(String email, UpdateProfileRequestDto dto) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setEmail(dto.getEmail());
        userRepository.save(user);

        return new UserProfileResponseDto(user.getEmail());
    }

    @Override
    public void deleteAccount(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }
}
