package com.example.signup.and.login.service;

import com.example.signup.and.login.DTO.LoginRequestDTO;
import com.example.signup.and.login.DTO.LoginResponseDTO;
import com.example.signup.and.login.entity.UsersEntity;
import com.example.signup.and.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountLockedException;
import java.time.LocalDateTime;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponseDTO login(LoginRequestDTO dto) {

        // step 1 — find user by email
        UsersEntity user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        // step 2 — check account is active

        // step 3 — check account is locked
        if (user.getLockedUntil() != null &&
                LocalDateTime.now().isBefore(user.getLockedUntil())) {
            throw new RuntimeException("Account is locked until "
                    + user.getLockedUntil());
        }

        // step 4 — check password
        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            if (user.getFailedLoginAttempts() >= 5) {
                user.setLockedUntil(LocalDateTime.now().plusMinutes(15));
                user.setFailedLoginAttempts(0);
            }
            userRepository.save(user);
            throw new RuntimeException("Invalid email or password");
        }

        // step 5 — reset on success
        user.setFailedLoginAttempts(0);
        user.setLockedUntil(null);
        userRepository.save(user);

        // step 6 — generate tokens
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = UUID.randomUUID().toString();

        return LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(900L)
                .build();
    }
}





