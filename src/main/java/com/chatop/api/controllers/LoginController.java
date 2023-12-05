package com.chatop.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;

import com.chatop.api.services.JWTService;

public class LoginController {
    private JWTService jwtService;

    public LoginController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("api/auth/login")
    public String getToken(Authentication authentication) {
        String token = jwtService.generateToken(authentication);
        return token;
    }
}