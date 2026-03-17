package com.example.signup.and.login.service;

import com.example.signup.and.login.DTO.SignUpRequestDTO;
import com.example.signup.and.login.config.ApplicationConfig;
import com.example.signup.and.login.entity.UsersEntity;
import com.example.signup.and.login.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    @Autowired
        private UserRepository userRepository;
   // @Autowired
    private PasswordEncoder passwordEncoder;

    public UsersEntity registerUser(SignUpRequestDTO dto){

    if (userRepository.findByEmail(dto.getEmail()).isPresent()){
        throw new RuntimeException("User with email already exists");
       }
    if(userRepository.findByPhoneNumber(dto.getPhoneNumber()).isPresent()){
        throw new RuntimeException("User with phone number already exists");
    }

    UsersEntity user = new UsersEntity();
    user.setFirstName(dto.getFirstName());
    user.setLastName(dto.getLastName());
    user.setEmail(dto.getEmail().toLowerCase());
    user.setPhoneNumber(dto.getPhoneNumber());
    user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));//hashing the password before saving to database

        user.setUuid(UUID.randomUUID());       // generate unique UUID
        user.setRole("USER");                  // default role
        user.setActive(true);                  // active by default
        user.setFailedLoginAttempts(0);

        return userRepository.save(user);
    }
}
