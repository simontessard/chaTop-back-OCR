package com.chatop.api.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.api.dto.UserDetailsDTO;
import com.chatop.api.models.User;
import com.chatop.api.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Service class for handling User operations.
 */
@Service
public class UserService {

    /**
     * The repository used for interacting with users in the database.
     */
    @Autowired
    UserRepository userRepository;

    /**
     * The password encoder used for encoding passwords.
     */
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    /**
     * Retrieves a user by its ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The retrieved user.
     * @throws UsernameNotFoundException if no user is found with the given ID.
     */
    @Transactional
    public User getUserById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User Not Found with id: " + id);
        }
    }

    /**
     * Retrieves a user by its username.
     *
     * @param username The username of the user to retrieve.
     * @return The retrieved user.
     * @throws UsernameNotFoundException if no user is found with the given
     *                                   username.
     */
    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
    }

    /**
     * Returns a UserDetailsDTO object for the given User.
     *
     * @param user The User object to convert to UserDetailsDTO.
     * @return A UserDetailsDTO object with the details of the given User.
     */
    public UserDetailsDTO getUserDetails(User user) {
        return new UserDetailsDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                formatDate(user.getCreated_at()),
                formatDate(user.getUpdated_at()));
    }

    /**
     * Formats a LocalDateTime object to a String in the format "yyyy/MM/dd".
     *
     * @param dateTime The LocalDateTime object to format.
     * @return A String representing the formatted date.
     */
    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return dateTime.format(formatter);
    }

    /**
     * Loads a user by its email.
     *
     * @param email The email of the user to load.
     * @return The loaded user.
     * @throws UsernameNotFoundException if no user is found with the given email.
     */
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        // Using username or name as email because database is not consistent
        String username = (user.getUsername() != null) ? user.getUsername() : user.getName();
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(),
                new ArrayList<>());
    }

    /**
     * Registers a new user.
     *
     * @param name     The name of the user.
     * @param email    The email of the user.
     * @param password The password of the user.
     * @return The registered user.
     * @throws RuntimeException if a user already exists with the given email.
     */
    public User register(String name, String email, String password) {
        User existingUser = userRepository.findByEmail(email);

        if (existingUser != null) {
            throw new RuntimeException("User already exists");
        }

        User newUser = new User();
        newUser.setEmail(email);
        newUser.setName(name);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUpdatedAt(LocalDateTime.now());
        newUser.setUsername(name);
        userRepository.save(newUser);
        return newUser;
    }
}
