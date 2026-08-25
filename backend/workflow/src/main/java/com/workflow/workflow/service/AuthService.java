package com.workflow.workflow.service;

import com.workflow.workflow.dto.RegisterRequestDto;
import com.workflow.workflow.dto.RegisterResponseDto;
import com.workflow.workflow.entity.User;
import com.workflow.workflow.exception.DuplicateResourceException;
import com.workflow.workflow.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.workflow.workflow.dto.LoginRequestDto;
import com.workflow.workflow.dto.LoginResponseDto;
import com.workflow.workflow.exception.InvalidCredentialsException;

/**
 * Service layer responsible for authentication-related
 * business logic.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor injection.
     *
     * BCryptPasswordEncoder is provided by Spring's
     * Application Context.
     */
    public AuthService(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user.
     */
    public RegisterResponseDto register(
            RegisterRequestDto requestDto) {

        /*
         * Business rule:
         * Email must be unique.
         */
        if (userRepository.existsByEmail(requestDto.getEmail())) {

            throw new DuplicateResourceException(
                    "User already exists with email: "
                            + requestDto.getEmail()
            );
        }

        /*
         * Create a new User entity.
         */
        User user = new User();

        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());

        /*
         * IMPORTANT:
         *
         * Never store the plain-text password.
         *
         * Example:
         *
         * "rahul123"
         *      ↓
         * BCrypt
         *      ↓
         * "$2a$10$..."
         */
        user.setPassword(
                passwordEncoder.encode(requestDto.getPassword())
        );

        /*
         * Role defaults to USER inside the Entity.
         */
        user.setRole("USER");

        /*
         * Save user in PostgreSQL.
         */
        User savedUser = userRepository.save(user);

        /*
         * Return only safe user information.
         * Password is intentionally excluded.
         */
        return new RegisterResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getRole()
        );
    }

    /**
     * Authenticates an existing user.
     *
     * Flow:
     * 1. Find user by email.
     * 2. Compare provided password with stored BCrypt hash.
     * 3. Return user information if credentials are valid.
     */
    public LoginResponseDto login(LoginRequestDto requestDto) {

        /*
         * Find user using email.
         *
         * We intentionally return the same generic error
         * for a missing user and wrong password.
         *
         * This avoids revealing whether an email exists
         * in our system.
         */
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException(
                                "Invalid email or password"
                        )
                );

        /*
         * Compare the plain-text password received from
         * the client against the BCrypt hash stored in DB.
         */
        boolean passwordMatches =
                passwordEncoder.matches(
                        requestDto.getPassword(),
                        user.getPassword()
                );

        if (!passwordMatches) {

            throw new InvalidCredentialsException(
                    "Invalid email or password"
            );
        }

        /*
         * Credentials are valid.
         *
         * Return safe user information.
         */
        return new LoginResponseDto(
                "Login successful",
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
}