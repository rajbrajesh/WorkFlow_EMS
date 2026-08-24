package com.workflow.workflow.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Entity representing an application user.
 *
 * This entity is responsible for authentication-related
 * user information and is intentionally kept separate
 * from the Employee entity.
 */
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * User's display name.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Email is used as the login identifier.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Password is stored as a BCrypt hash.
     *
     * Plain-text passwords must NEVER be stored.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Role will be used later for authorization.
     *
     * For now every registered user can have USER role.
     */
    @Column(nullable = false)
    private String role = "USER";

    /**
     * Stores when the user was created.
     */
    @Column(nullable = false)
    private LocalDateTime createdAt;

    public User() {
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}