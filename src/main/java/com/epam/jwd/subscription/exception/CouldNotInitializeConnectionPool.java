package com.epam.jwd.subscription.exception;

public class CouldNotInitializeConnectionPool extends Error {

    private static final long serialVersionUID = 5127115981446846518L;

    public CouldNotInitializeConnectionPool(String message, Throwable cause) {
        super(message, cause);
    }
}
