package com.chatop.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.chatop.api.services.JWTService;

@RestController
public class LoginController {

    private JWTService jwtService;

    public LoginController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/user")
    public String getUser() {
        return "Welcome, User";
    }

    @GetMapping("/admin")
    public String getAdmin() {
        return "Welcome, Admin" + "\n" + "You have access to all the resources";
    }

    @PostMapping("/login")
    public String getToken(Authentication authentication) {
        String token = jwtService.generateToken(authentication);
        return token;
    }

}