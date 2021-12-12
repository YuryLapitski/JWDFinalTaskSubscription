package com.epam.jwd.subscription.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountValidatorTest {

    AccountValidator accountValidator;

    @BeforeAll
    void init() {
        accountValidator = AccountValidator.getInstance();
    }

    @Test
    void isLoginValidPositive() {
        final String login = "Sam_Smith-1";
        assertTrue(accountValidator.isLoginValid(login));
    }

    @Test
    void isLoginValidNegative() {
        final String login = "Sam&Smith!1";
        assertFalse(accountValidator.isLoginValid(login));
    }

    @Test
    void isPasswordCorrectPositive() {
        final String pass = "Test1";
        assertTrue(accountValidator.isLoginValid(pass));
    }

    @Test
    void isPasswordCorrectNegative() {
        final String pass = "";
        assertFalse(accountValidator.isLoginValid(pass));
    }

    @Test
    void validateAllPositive() {
        final String login = "Sam_Smith-1";
        final String pass = "Test1";
        assertTrue(accountValidator.validateAll(login, pass));
    }

    @Test
    void validateAllNegative() {
        final String login = "Sam&Smith!1";
        final String pass = "Test1";
        assertFalse(accountValidator.validateAll(login, pass));
    }
}