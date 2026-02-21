package com.bab.grocery_backend.dto.dtoRequest;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginRequestDto {

    @Email
    private String email;

    private String password;
}
