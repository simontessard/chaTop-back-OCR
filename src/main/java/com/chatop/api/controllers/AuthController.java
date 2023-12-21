package com.chatop.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.chatop.api.dto.LoginDTO;
import com.chatop.api.dto.NewUserDTO;
import com.chatop.api.dto.TokenDTO;
import com.chatop.api.models.User;

import com.chatop.api.services.JWTService;
import com.chatop.api.services.UserService;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/auth")
public class AuthController {
    private final JWTService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "Login an user to get him a token")
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> getToken(@RequestBody LoginDTO user) {
        // System.out.println(user.getLogin());

        UserDetails userDetails;
        try {
            userDetails = userService.loadUserByEmail(user.getLogin());
        } catch (UsernameNotFoundException e) {
            System.out.println("User not found");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!user.getPassword().equals(userDetails.getPassword())) {
            System.out.println("Password does not match");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication);
        TokenDTO response = new TokenDTO(token);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Register a new user and get him a token")
    @PostMapping("/register")
    public ResponseEntity<TokenDTO> register(@RequestBody NewUserDTO user) {
        User newUser = userService.register(user.getUsername(), user.getPassword());
        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser.getUsername(),
                newUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtService.generateToken(authentication);
        TokenDTO response = new TokenDTO(token);
        return ResponseEntity.ok(response);
    }
}