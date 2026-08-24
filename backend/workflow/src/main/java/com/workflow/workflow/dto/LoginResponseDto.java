package com.workflow.workflow.dto;

/**
 * DTO returned after successful login.
 *
 * Password is intentionally not included.
 *
 * JWT/token authentication will be introduced later.
 */
public class LoginResponseDto {

    private String message;
    private Long userId;
    private String name;
    private String email;
    private String role;

    public LoginResponseDto() {
    }

    public LoginResponseDto(
            String message,
            Long userId,
            String name,
            String email,
            String role) {

        this.message = message;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
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
}