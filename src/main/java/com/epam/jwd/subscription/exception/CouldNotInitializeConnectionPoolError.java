package com.epam.jwd.subscription.exception;

public class CouldNotInitializeConnectionPoolError extends Error {

    private static final long serialVersionUID = 5127115981446846518L;

    public CouldNotInitializeConnectionPoolError(String message, Throwable cause) {
        super(message, cause);
    }
}
