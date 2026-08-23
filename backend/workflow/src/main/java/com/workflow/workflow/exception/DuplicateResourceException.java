package com.workflow.workflow.exception;

/**
 * Exception thrown when a resource conflicts with
 * an existing resource.
 *
 * Example:
 * Trying to create an employee using an email
 * that already exists.
 */
public class DuplicateResourceException extends RuntimeException {

    public DuplicateResourceException(String message) {
        super(message);
    }
}