package com.user.Exceptions;

public class RegistrationException extends Exception{

	// Constructor that accepts a message
    public RegistrationException(String message) {
        super(message);
    }

    // Constructor that accepts both a message and a cause
    public RegistrationException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
