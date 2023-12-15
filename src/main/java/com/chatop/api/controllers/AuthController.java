package com.chatop.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.HashMap;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

import com.chatop.api.dto.TokenDTO;
import com.chatop.api.models.User;

import com.chatop.api.services.JWTService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final JWTService jwtService;

    @Operation(summary = "Login an user to get him a token")
    @PostMapping("/api/auth/login")
    public ResponseEntity<TokenDTO> getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = jwtService.generateToken(authentication);
        TokenDTO response = new TokenDTO(token);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register a new user and get him a token")
    @PostMapping("/api/auth/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User newUser = jwtService.register(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(newUser);
    }
}