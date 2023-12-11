package com.chatop.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import java.util.HashMap;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

import com.chatop.api.models.Rental;
import com.chatop.api.models.User;
import java.util.Optional;

import com.chatop.api.services.JWTService;
import com.chatop.api.services.RentalService;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final JWTService jwtService;
    private final RentalService rentalService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<Map<String, String>> getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String token = jwtService.generateToken(authentication);
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        User newUser = jwtService.register(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/api/rentals/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        return rental.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}