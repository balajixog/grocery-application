package com.bab.grocery_backend.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bab.grocery_backend.dto.dtoRequest.UpdateProfileRequestDto;
import com.bab.grocery_backend.dto.dtoRequest.UserProfileResponseDto;
import com.bab.grocery_backend.entity.PasswordResetToken;
import com.bab.grocery_backend.entity.User;
import com.bab.grocery_backend.repository.PasswordResetTokenRepository;
import com.bab.grocery_backend.repository.UserRepository;
import com.bab.grocery_backend.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;

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
    @Override
    public void forgotPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .build();

        passwordResetTokenRepository.save(resetToken);

        // In real app -> send email. For now, just log token.
        System.out.println("RESET TOKEN: " + token);
    }

    @Override
    public void resetPassword(String token, String newPassword) {

        PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid token"));

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(resetToken);
    }
}
