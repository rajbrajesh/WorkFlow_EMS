package com.workflow.workflow.exception;

/**
 * Exception thrown when authentication credentials
 * are invalid.
 *
 * We use the same exception for:
 * - Unknown email
 * - Incorrect password
 *
 * This prevents exposing which emails exist in our DB.
 */
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}