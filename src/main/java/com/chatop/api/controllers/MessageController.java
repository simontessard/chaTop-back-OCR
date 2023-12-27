package com.chatop.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.models.Message;
import com.chatop.api.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import com.chatop.api.dto.MessageDTO;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @Operation(summary = "Create a message between an user and a rental")
    @PostMapping("/api/messages")
    public ResponseEntity<Message> createMessage(@Valid @RequestBody MessageDTO payload) {
        Message savedMessage = messageService.createMessage(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMessage);
    }
}
