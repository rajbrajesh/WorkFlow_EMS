package com.workflow.workflow.repository;

import com.workflow.workflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository layer for User database operations.
 *
 * JpaRepository provides standard CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user using their email.
     *
     * Email will be our login identifier.
     */
    Optional<User> findByEmail(String email);

    /**
     * Checks whether a user already exists with
     * the given email.
     *
     * This will be used during registration.
     */
    boolean existsByEmail(String email);
}