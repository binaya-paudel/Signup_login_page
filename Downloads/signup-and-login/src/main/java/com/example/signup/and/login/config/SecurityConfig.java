package com.example.signup.and.login.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(
                            "/auth/signup",
                            "/auth/login",
                            "/auth/forgot-password",  // ← add here
                            "/auth/reset-password"    // ← add here
                    ).permitAll()
                    .anyRequest().authenticated()
            );
    return http.build();}
}
