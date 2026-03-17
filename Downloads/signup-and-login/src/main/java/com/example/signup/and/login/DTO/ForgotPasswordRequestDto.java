package com.example.signup.and.login.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequestDto {

    @NotBlank(message = "email is required")
    @Email(message = "Please provide a valid email address")
    private String email;
}
