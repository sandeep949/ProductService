package com.project.ProductService.Service;

import com.project.ProductService.DTO.Role;
import com.project.ProductService.DTO.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtService {

    private static String secretKey;

    // Inject the secret key via @Value
    @Value("${application.security.jwt.secret-key}")

    public void setSecretKey(String secretKey) {
        JwtService.secretKey = secretKey; // Assign to the static field
    }

    /**
     * Static method to parse a JWT token and extract user details.
     *
     * @param token The JWT token.
     * @return UserDTO containing user details, or null if the token is invalid.
     */
    public static UserDTO parseToken(String token) {
        try {
            // Decode the secret key
            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));

            // Parse the claims from the token
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key) // Use the decoded key
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            System.out.println(claims.getSubject()+"subject");
            // Map claims to UserDTO
            UserDTO userDTO = new UserDTO();
            userDTO.setUsername(claims.getSubject()); // Assuming 'sub' holds the user ID
            userDTO.setRole(Role.valueOf(claims.get("role", String.class))); // Assuming 'role' is a claim
            return userDTO;
        } catch (Exception e) {
            e.printStackTrace(); // Log error
            return null; // Invalid token
        }
    }
}
