package com.example.signup.and.login.service;

import com.example.signup.and.login.DTO.ForgotPasswordRequestDto;
import com.example.signup.and.login.DTO.ResetPasswordRequestDto;
import com.example.signup.and.login.entity.PasswordResetTokenEntity;
import com.example.signup.and.login.entity.UsersEntity;
import com.example.signup.and.login.repository.PasswordResetTokenRepository;
import com.example.signup.and.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Value("${app.reset-token.expiry-minutes}")
    private int expiryMinutes;

    @Transactional
    public void initiatePasswordReset(ForgotPasswordRequestDto dto){
        Optional<UsersEntity> userOptional = userRepository.findByEmail(dto.getEmail());

                if(userOptional.isEmpty()){
                    return;
                }

                UsersEntity user = userOptional.get();

                passwordResetTokenRepository.deleteByUser(user);

        String token = UUID.randomUUID().toString()
                + UUID.randomUUID().toString();

        PasswordResetTokenEntity resetToken = new PasswordResetTokenEntity();
        resetToken.setUser(user);
        resetToken.setToken(token);
        resetToken.setExpiredAt(LocalDateTime.now().plusMinutes(expiryMinutes));
        resetToken.setUsed(false);
        passwordResetTokenRepository.save(resetToken);

        //emailService.sendPasswordResetEmail(user.getEmail(),token);
        try {
            emailService.sendPasswordResetEmail(user.getEmail(), token);
            System.out.println("Email sent successfully to: " + user.getEmail());
        } catch (Exception e) {
            System.out.println("Email sending failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
  @Transactional
    public void resetPassword(ResetPasswordRequestDto dto){
        PasswordResetTokenEntity resetToken =
                passwordResetTokenRepository.findByToken(dto.getToken())
                        .orElseThrow(() -> new RuntimeException("Invalid token or token expired"));

        if(resetToken.getUsed()){
            throw new RuntimeException("Token has already been used");
        }

        if(LocalDateTime.now().isAfter(resetToken.getExpiredAt())){
            throw new RuntimeException("Token has expired. Please request a new password reset.");
        }

        UsersEntity users = resetToken.getUser();
        users.setPasswordHash(passwordEncoder.encode(dto.getNewPassword()));
        users.setFailedLoginAttempts(0);
        users.setLockedUntil(null);
        userRepository.save(users);

        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
    }


}
