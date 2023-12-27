package com.chatop.api.services;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import com.chatop.api.dto.MessageDTO;
import com.chatop.api.models.Message;
import com.chatop.api.models.Rental;
import com.chatop.api.models.User;
import com.chatop.api.repository.MessageRepository;

/**
 * Service class for handling Message operations.
 */
@Service
public class MessageService {
    /**
     * The repository used for interacting with messages in the database.
     */
    private final MessageRepository messageRepository;
    private final UserService userService;
    private final RentalService rentalService;

    /**
     * Constructor for the MessageService class.
     *
     * @param messageRepository The repository to use for interacting with messages
     *                          in the database.
     */
    public MessageService(MessageRepository messageRepository, UserService userService, RentalService rentalService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
        this.rentalService = rentalService;
    }

    /**
     * Creates a new message and saves it in the database.
     *
     * The creation and update timestamps of the message are set to the current
     * time.
     *
     * @param newMessage The message to create.
     * @return The created message.
     */
    public Message createMessage(MessageDTO payload) {
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
        newMessage.setCreated_at(new Timestamp(System.currentTimeMillis()));
        newMessage.setUpdated_at(newMessage.getCreated_at());

        return messageRepository.save(newMessage);
    }

    /**
     * Saves a message in the database.
     *
     * @param message The message to save.
     * @return The saved message.
     */
    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
