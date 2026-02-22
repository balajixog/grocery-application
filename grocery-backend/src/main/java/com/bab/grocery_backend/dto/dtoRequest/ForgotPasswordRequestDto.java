package com.bab.grocery_backend.dto.dtoRequest;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ForgotPasswordRequestDto {
    @Email
    private String email;
}