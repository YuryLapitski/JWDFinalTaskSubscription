package com.epam.jwd.subscription.exception;

public class ResourcesLoadingException extends RuntimeException {

    public ResourcesLoadingException(String message) {
        super(message);
    }

    public ResourcesLoadingException(String message, Throwable cause) {
        super(message, cause);

    }
}
