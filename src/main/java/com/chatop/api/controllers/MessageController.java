package com.chatop.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.models.Message;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;

import com.chatop.api.services.MessageService;
import com.chatop.api.services.RentalService;
import com.chatop.api.services.UserService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.chatop.api.dto.MessageDTO;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;
    private final RentalService rentalService;

    @Operation(summary = "Create a message between an user and a rental")
    @PostMapping("/api/messages")
    public ResponseEntity<Message> createMessage(@Valid @RequestBody MessageDTO payload) {
        String message = payload.getMessage();
        Integer userId = payload.getUserId();
        Integer rentalId = payload.getRentalId();

        if (userId == null || rentalId == null) {
            throw new IllegalArgumentException("User ID and Rental ID must not be null");
        }

        User user = userService.getUserById(userId);
        Rental rental = rentalService.getRentalById(rentalId)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        Message newMessage = new Message();
        newMessage.setMessage(message);
        newMessage.setUser(user);
        newMessage.setRental(rental);

        Message savedMessage = messageService.createMessage(newMessage);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }
}
