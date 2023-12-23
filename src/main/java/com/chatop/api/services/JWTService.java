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

/**
 * Service class for handling JSON Web Token (JWT) operations.
 */
@Service
public class JWTService {
    /**
     * The JWT encoder used for encoding JWTs.
     */
    private JwtEncoder jwtEncoder;

    /**
     * Constructor for the JWTService class.
     *
     * @param jwtEncoder The JWT encoder to use for encoding JWTs.
     */
    public JWTService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    /**
     * Generates a JWT for the given authentication.
     *
     * The JWT's subject is the name of the authentication, and it is issued by
     * "self".
     * The JWT expires 1 day after it is issued.
     *
     * @param authentication The authentication for which to generate a JWT.
     * @return The generated JWT, or null if an error occurred.
     */
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
}
