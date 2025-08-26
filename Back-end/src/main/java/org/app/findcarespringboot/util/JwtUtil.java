package org.app.findcarespringboot.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil { // Helper class for JWT operations
    private final String SECRET_KEY = "TPTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT"; // Key for signing and verifying tokens

    public String generateToken(String username) { // Create a new token
        return Jwts.builder() // Start building
                .setSubject(username) // Store username in the token
                .setIssuedAt(new Date()) // Add issue time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // Expire in 1 hour
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Sign using our secret and HS256 algorithm
                .compact(); // Produce final token string
    }

    public String extractUsername(String token) { // Read username from token
        return Jwts.parser() // Start parsing
                .setSigningKey(SECRET_KEY) // Use our secret to verify
                .parseClaimsJws(token) // Parse claims
                .getBody() // Get token body
                .getSubject(); // Extract username
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token); // verifies token
            return true; // valid
        } catch (JwtException e) {
            return false; // invalid or expired
        }
    }
}