package com.chatop.api.services;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import com.chatop.api.models.Message;
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

    /**
     * Constructor for the MessageService class.
     *
     * @param messageRepository The repository to use for interacting with messages
     *                          in the database.
     */
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
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
    public Message createMessage(Message newMessage) {
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
