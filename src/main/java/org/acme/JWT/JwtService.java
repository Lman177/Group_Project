package org.acme.JWT;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.Set;

@ApplicationScoped
public class JwtService {

    private static final String ISSUER = "namlimmo"; // Replace with your issuer
    private static final int TOKEN_EXPIRATION_SECONDS = 3600; // Token expiration time in seconds (e.g., 1 hour)

    public String generateJwt(String username) {
        Instant expirationTime = Instant.now().plusSeconds(TOKEN_EXPIRATION_SECONDS);

        return Jwt.claims()
                .issuer(ISSUER)
                .subject(username)
                .expiresAt(expirationTime)
                .groups(Set.of("user")) // Optional: Set user roles/groups
                // Add more claims as needed
                .sign();
    }
}