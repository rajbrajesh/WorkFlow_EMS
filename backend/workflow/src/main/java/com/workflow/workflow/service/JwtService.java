package com.workflow.workflow.service;

import com.workflow.workflow.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Service responsible for JWT operations.
 *
 * Responsibilities:
 * - Generate JWT tokens
 * - Extract information from JWT tokens
 * - Validate JWT tokens
 */
@Service
public class JwtService {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    /**
     * Constructor injection.
     *
     * JwtConfig contains our JWT secret and expiration time.
     */
    public JwtService(JwtConfig jwtConfig) {

        this.jwtConfig = jwtConfig;

        /*
         * Convert the configured secret string into
         * a cryptographic SecretKey.
         */
        this.secretKey = Keys.hmacShaKeyFor(
                jwtConfig.getSecret()
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * Generates a JWT token for the given email.
     *
     * The email will be stored as the JWT subject.
     *
     * Generates a JWT containing the user's email and role.
     *
     * @param email user's email
     * @param role user's application role
     * @return generated JWT token
     */
    public String generateToken(String email, String role) {

        // Capture the token creation time.
        Date issuedAt = new Date();

        // Calculate when the token should expire.
        Date expiration = new Date(
                issuedAt.getTime() + jwtConfig.getExpiration()
        );

        return Jwts.builder()

                /*
                 * Subject identifies the user.
                 *
                 * In our application the user's email
                 * will be used as the subject.
                 *
                 * Store the user's email as the JWT subject.
                 */
                .subject(email)

                // Store the user's role as a custom JWT claim.
                .claim("role", role)

                /*
                 * Time when the token was created.
                 * - Store token creation time.
                 */
                .issuedAt(issuedAt)

                /*
                 * Time when the token expires.
                 * -Store token expiration time.
                 */
                .expiration(expiration)

                /*
                 * Sign the JWT using our secret key.
                 * -Sign the token so it cannot be modified without knowing our secret key.
                 */
                .signWith(secretKey)

                /*
                 * Build the final JWT string.
                 * - Convert JWT builder into the final token string.
                 */
                .compact();
    }

    /**
     * Extracts the email from the JWT.
     *
     * The email is stored in the "sub" (subject) claim.
     */
    public String extractEmail(String token) {

        return extractAllClaims(token)
                .getSubject();
    }

    /**
     * Extracts the user's role from the JWT.
     *
     * @param token JWT token
     * @return role stored inside the token
     */
    public String extractRole(String token) {

        // Read all claims from the validated JWT.
        return extractAllClaims(token).get("role", String.class);
    }

    /**
     * Checks whether a JWT has expired.
     */
    public boolean isTokenExpired(String token) {

        Date expiration =
                extractAllClaims(token).getExpiration();

        return expiration.before(new Date());
    }

    /**
     * Validates a JWT against the expected email.
     *
     * A token is valid when:
     * 1. The email inside the token matches the expected email.
     * 2. The token has not expired.
     */
    public boolean isTokenValid(
            String token,
            String email) {

        String tokenEmail = extractEmail(token);

        return tokenEmail.equals(email)
                && !isTokenExpired(token);
    }

    /**
     * Parses the JWT and returns all claims.
     *
     * Claims are the pieces of information stored inside
     * the JWT payload.
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()

                /*
                 * Tell JJWT which key should be used
                 * to verify the token signature.
                 */
                .verifyWith(secretKey)

                .build()

                /*
                 * Parse and verify the signed JWT.
                 */
                .parseSignedClaims(token)

                /*
                 * Extract the payload/claims.
                 */
                .getPayload();
    }
}