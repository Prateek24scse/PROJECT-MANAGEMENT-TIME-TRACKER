package com.timetracker.exception;

/**
 * Custom exception for database-related errors
 * Demonstrates exception handling in OOP
 */
public class DatabaseException extends Exception {
    
    public DatabaseException(String message) {
        super(message);
    }
    
    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

