package com.bab.grocery_backend.dto.dtoRequest;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequestDto {
    @NotBlank
    private String token;

    @NotBlank
    private String newPassword;
}