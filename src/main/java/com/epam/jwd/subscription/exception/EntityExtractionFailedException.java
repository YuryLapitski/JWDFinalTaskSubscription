package com.epam.jwd.subscription.exception;

public class EntityExtractionFailedException extends Exception {

    private static final long serialVersionUID = 497176134428297825L;

    public EntityExtractionFailedException(String message) {
        super(message);
    }

    public EntityExtractionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
