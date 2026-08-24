package com.workflow.workflow.controller;

import com.workflow.workflow.dto.RegisterRequestDto;
import com.workflow.workflow.dto.RegisterResponseDto;
import com.workflow.workflow.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.workflow.workflow.dto.LoginRequestDto;
import com.workflow.workflow.dto.LoginResponseDto;

/**
 * REST Controller for authentication-related APIs.
 *
 * Current responsibility:
 * - User registration
 *
 * Login will be added next.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * POST /api/auth/register
     *
     * Registers a new user.
     */
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(
            @Valid @RequestBody RegisterRequestDto requestDto) {

        RegisterResponseDto response =
                authService.register(requestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    /**
     * POST /api/auth/login
     *
     * Authenticates an existing user.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto requestDto) {

        LoginResponseDto response =
                authService.login(requestDto);

        return ResponseEntity.ok(response);
    }
}