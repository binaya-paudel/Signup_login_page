package com.example.signup.and.login.controller;

import com.example.signup.and.login.DTO.ForgotPasswordRequestDto;
import com.example.signup.and.login.DTO.LoginRequestDTO;
import com.example.signup.and.login.DTO.LoginResponseDTO;
import com.example.signup.and.login.DTO.ResetPasswordRequestDto;
import com.example.signup.and.login.service.AuthService;
import com.example.signup.and.login.service.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountLockedException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    private final PasswordResetService passwordResetService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        LoginResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?>forgotPassword(
            @Valid @RequestBody ForgotPasswordRequestDto dto){
        passwordResetService.initiatePasswordReset(dto);
        return ResponseEntity.ok("Password reset link sent to email if it exists");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequestDto dto){
        passwordResetService.resetPassword(dto);
        return ResponseEntity.ok("Password has been reset successfully");
    }

    @Value("${mailusername}")
    private String mailUsername;

    @GetMapping("/test-env")
    public String testEnv() {
        return "Mail username: " + mailUsername;
    }
}