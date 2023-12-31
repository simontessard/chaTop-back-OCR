package com.chatop.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.chatop.api.dto.UserDetailsDTO;
import com.chatop.api.models.User;
import com.chatop.api.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get the authenticated user's details")
    @GetMapping("api/auth/me")
    public ResponseEntity<UserDetailsDTO> getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        UserDetailsDTO userDetails = userService.getUserDetails(user);
        return ResponseEntity.ok(userDetails);
    }

    @Operation(summary = "Get user's details by id")
    @GetMapping("api/user/{id}")
    public ResponseEntity<UserDetailsDTO> getUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        UserDetailsDTO userDetails = userService.getUserDetails(user);
        return ResponseEntity.ok(userDetails);
    }
}
