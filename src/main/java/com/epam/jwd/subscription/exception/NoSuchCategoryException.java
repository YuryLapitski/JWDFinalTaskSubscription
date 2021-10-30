package com.epam.jwd.subscription.exception;

public class NoSuchCategoryException extends Exception {

    private static final long serialVersionUID = 4295725907935337076L;

    public NoSuchCategoryException(String message) {
        super(message);
    }
}
