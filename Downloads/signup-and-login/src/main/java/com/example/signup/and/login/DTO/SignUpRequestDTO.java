package com.example.signup.and.login.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignUpRequestDTO {

    @NotBlank(message = "email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message="firstName is required")
    @Size(min =3, max=20, message = "firstName must be between 3 and 20 characters")
    private String firstName;

    @NotBlank(message="lastName is required")
    @Size(min =3, max=20, message = "lastName must be between 3 and 20 characters")
    private String lastName;

    @NotBlank(message="password is required")
    @Size(min = 8, message = "password must be at least 8 characters long")
    private String password;

    @NotBlank(message="phone number is required")
    @Size(min = 10, max = 15, message = "phone number must be between 10 and 15 characters")
    private Long phoneNumber;







}
