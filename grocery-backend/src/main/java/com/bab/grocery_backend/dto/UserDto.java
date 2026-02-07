package com.bab.grocery_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;


public class UserDto {
    @Email
    String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$\n")
    String password;
}
