package com.chatop.api.services;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import com.chatop.api.models.Message;
import com.chatop.api.repository.MessageRepository;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message createMessage(Message newMessage) {
        newMessage.setCreated_at(new Timestamp(System.currentTimeMillis()));
        newMessage.setUpdated_at(newMessage.getCreated_at());
        return messageRepository.save(newMessage);
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
