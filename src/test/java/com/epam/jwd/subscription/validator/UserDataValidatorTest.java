package com.epam.jwd.subscription.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserDataValidatorTest {

    UserDataValidator userDataValidator;

    @BeforeAll
    void init() {
        userDataValidator = UserDataValidator.getInstance();
    }

    @Test
    void isFirstNameValidPositive() {
        final String firstName = "Ivan";
        assertTrue(userDataValidator.isFirstNameValid(firstName));
    }

    @Test
    void isFirstNameValidNegative() {
        final String firstName = "Ivan12!";
        assertFalse(userDataValidator.isFirstNameValid(firstName));
    }

    @Test
    void isLastNameValidPositive() {
        final String lastName = "Ivanov";
        assertTrue(userDataValidator.isLastNameValid(lastName));
    }

    @Test
    void isLastNameValidNegative() {
        final String lastName = "Ivanov12!";
        assertFalse(userDataValidator.isLastNameValid(lastName));
    }

    @Test
    void isAgeValidPositive() {
        final int age = 32;
        assertTrue(userDataValidator.isAgeValid(age));
    }

    @Test
    void isAgeValidNegative() {
        final int age = 150;
        assertFalse(userDataValidator.isAgeValid(age));
    }

    @Test
    void isEmailValidPositive() {
        final String email = "ivan_petr12@inbox.ru";
        assertTrue(userDataValidator.isEmailValid(email));
    }

    @Test
    void isEmailValidNegative() {
        final String email = "ivan@petr12@inbox.1ru";
        assertFalse(userDataValidator.isEmailValid(email));
    }

    @Test
    void validateAllPositive() {
        final String firstName = "Ivan";
        final String lastName = "Ivanov";
        final int age = 32;
        final String email = "ivan_petr12@inbox.ru";
        assertTrue(userDataValidator.validateAll(firstName, lastName, age, email));
    }

    @Test
    void validateAllNegative() {
        final String firstName = "Ivan12!";
        final String lastName = "Ivanov43!";
        final int age = -5;
        final String email = "ivan@petr12@inbox.8ru";
        assertFalse(userDataValidator.validateAll(firstName, lastName, age, email));
    }
}