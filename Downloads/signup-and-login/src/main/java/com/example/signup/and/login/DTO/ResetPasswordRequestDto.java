package com.example.signup.and.login.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ResetPasswordRequestDto {
@NotBlank(message = "token is required")
private String token;

@NotBlank(message = "new password is required")
    @Size(min=8, message = "new password must be at least 8 characters long")
    @JsonProperty("new_password")
private String newPassword;
}
