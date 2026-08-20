package com.workflow.workflow.exception;

/**
 * Custom exception used when a requested resource
 * does not exist in the database.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}