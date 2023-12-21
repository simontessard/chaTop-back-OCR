package com.chatop.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.api.models.User;
import com.chatop.api.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public User getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User Not Found with id: " + id);
        }
    }

    public User register(String username, String password) {

        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            throw new RuntimeException("User already exists");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);
        return newUser;
    }
}
