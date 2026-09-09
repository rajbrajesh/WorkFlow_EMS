package com.workflow.workflow.dto;

/**
 * DTO returned after successful login.
 *
 * Password is intentionally not included.
 *
 * JWT token is returned so the client can use it
 * for authenticated API requests.
 */
public class LoginResponseDto {

    private String message;
    private Long userId;
    private String name;
    private String email;
    private String role;
    private String token;

    public LoginResponseDto() {
    }

    public LoginResponseDto(
            String message,
            Long userId,
            String name,
            String email,
            String role,
            String token) {

        this.message = message;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
        return token;
    }
}