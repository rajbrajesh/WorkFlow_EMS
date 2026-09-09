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
     */
    public String generateToken(String email) {

        Date issuedAt = new Date();

        Date expiration = new Date(
                issuedAt.getTime() + jwtConfig.getExpiration()
        );

        return Jwts.builder()

                /*
                 * Subject identifies the user.
                 *
                 * In our application the user's email
                 * will be used as the subject.
                 */
                .subject(email)

                /*
                 * Time when the token was created.
                 */
                .issuedAt(issuedAt)

                /*
                 * Time when the token expires.
                 */
                .expiration(expiration)

                /*
                 * Sign the JWT using our secret key.
                 */
                .signWith(secretKey)

                /*
                 * Build the final JWT string.
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