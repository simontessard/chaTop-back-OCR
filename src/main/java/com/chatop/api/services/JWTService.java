package com.chatop.api.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.core.Authentication;

import org.springframework.stereotype.Service;

import com.chatop.api.models.User;
import com.chatop.api.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class JWTService {
    private JwtEncoder jwtEncoder;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public JWTService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {
        try {
            if (authentication == null) {
                System.out.println("Authentication is null");
                return null;
            }

            Instant now = Instant.now();
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plus(1, ChronoUnit.DAYS))
                    .subject(authentication.getName())
                    .build();
            JwtEncoderParameters jwtEncoderParameters = JwtEncoderParameters
                    .from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);
            return this.jwtEncoder.encode(jwtEncoderParameters).getTokenValue();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public User register(String username, String password) {

        User existingUser = userRepository.findByUsername(username);
        if (existingUser != null) {
            throw new RuntimeException("User already exists");
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password)); // encode the password
        userRepository.save(newUser);
        return newUser;
    }
}
