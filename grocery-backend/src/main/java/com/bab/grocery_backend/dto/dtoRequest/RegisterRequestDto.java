package com.bab.grocery_backend.dto.dtoRequest;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequestDto {

    @Email
    private String email;

    @Pattern(
      regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&.])[A-Za-z\\d@$!%*?&.]{8,}$",
      message = "Password must be strong"
    )
    private String password;
    @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid Indian phone number")
    private String phone;
}
