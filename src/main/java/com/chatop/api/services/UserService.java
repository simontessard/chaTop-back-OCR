package com.chatop.api.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
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

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public User register(String name, String email, String password) {
        User existingUser = userRepository.findByEmail(email);

        if (existingUser != null) {
            throw new RuntimeException("User already exists");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);
        return newUser;
    }
}
