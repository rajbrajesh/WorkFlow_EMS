package com.workflow.workflow.exception;

import java.util.Map;

/**
 * Standard error response returned by our REST API.
 *
 * This gives the frontend a consistent structure
 * for handling different types of errors.
 */
public class ApiError {

    private int status;

    private String message;

    // Used for field-level validation errors.
    private Map<String, String> errors;

    public ApiError() {
    }

    // Constructor for errors without field-specific details.
    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Constructor for validation errors.
    public ApiError(
            int status,
            String message,
            Map<String, String> errors) {

        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}