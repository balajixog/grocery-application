package com.bab.grocery_backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bab.grocery_backend.dto.AuthResponseDto;
import com.bab.grocery_backend.dto.LoginRequestDto;
import com.bab.grocery_backend.dto.RegisterRequestDto;
import com.bab.grocery_backend.entity.User;
import com.bab.grocery_backend.repository.UserRepository;
import com.bab.grocery_backend.service.UserService;
import com.bab.grocery_backend.util.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterRequestDto dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Account already exists");
        }

        userService.createUser(
                User.builder()
                        .email(dto.getEmail())
                        .password(passwordEncoder.encode(dto.getPassword()))
                        .role("ROLE_USER") 
                        .build()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Successfully Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @Valid @RequestBody LoginRequestDto dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Account not found"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }

        String token = jwtUtil.generateToken(user.getEmail(),user.getRole());

        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}
