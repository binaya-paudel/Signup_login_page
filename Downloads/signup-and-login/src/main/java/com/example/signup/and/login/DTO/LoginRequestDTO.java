package com.example.signup.and.login.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @NotBlank(message="email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message="password is required")
    private String password;
}
