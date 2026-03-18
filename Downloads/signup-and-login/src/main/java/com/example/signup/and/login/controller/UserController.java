package com.example.signup.and.login.controller;

import com.example.signup.and.login.DTO.SignUpRequestDTO;
import com.example.signup.and.login.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserController {
@Autowired
    private  UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup (@Valid @RequestBody SignUpRequestDTO dto){
        try {
            userService.registerUser(dto);
            return ResponseEntity.status(201).body("User registered successfully");
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
