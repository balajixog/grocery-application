package com.bab.grocery_backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.bab.grocery_backend.dto.dtoRequest.UpdateProfileRequestDto;
import com.bab.grocery_backend.dto.dtoRequest.UserProfileResponseDto;
import com.bab.grocery_backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user/profile")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserProfileResponseDto> getProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(userService.getProfile(email));
    }

    @PutMapping
    public ResponseEntity<UserProfileResponseDto> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequestDto dto) {

        String email = authentication.getName();
        return ResponseEntity.ok(userService.updateProfile(email, dto));
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount(Authentication authentication) {
        String email = authentication.getName();
        userService.deleteAccount(email);
        return ResponseEntity.ok("Account deleted successfully");
    }
}