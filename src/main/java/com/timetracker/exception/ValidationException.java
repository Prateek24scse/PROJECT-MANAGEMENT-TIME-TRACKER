package com.timetracker.exception;

/**
 * Custom exception for validation errors
 * Demonstrates exception handling in OOP
 */
public class ValidationException extends Exception {
    
    public ValidationException(String message) {
        super(message);
    }
}

