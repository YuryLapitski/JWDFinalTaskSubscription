package com.epam.jwd.subscription.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EditionValidatorTest {

    EditionValidator editionValidator;

    @BeforeAll
    void init() {
        editionValidator = EditionValidator.getInstance();
    }

    @Test
    void isNameValidPositive() {
        final String name = "The-New.York, & Times 5!?";
        assertTrue(editionValidator.isNameValid(name));
    }

    @Test
    void isNameValidNegative() {
        final String name = "The New York > Times /";
        assertFalse(editionValidator.isNameValid(name));
    }

    @Test
    void isNameValidEmpty() {
        final String name = "";
        assertFalse(editionValidator.isNameValid(name));
    }

    @Test
    void isThreeMonthsPriceValidPositive() {
        final String price = "54.81";
        assertTrue(editionValidator.isThreeMonthsPriceValid(price));
    }

    @Test
    void isThreeMonthsPriceValidNegative() {
        final String price = "as54";
        assertFalse(editionValidator.isThreeMonthsPriceValid(price));
    }

    @Test
    void isThreeMonthsPriceValidEmpty() {
        final String price = "";
        assertFalse(editionValidator.isThreeMonthsPriceValid(price));
    }

    @Test
    void isSixMonthsPriceValidPositive() {
        final String price = "54.81";
        assertTrue(editionValidator.isSixMonthsPriceValid(price));
    }

    @Test
    void isSixMonthsPriceValidNegative() {
        final String price = "as54";
        assertFalse(editionValidator.isSixMonthsPriceValid(price));
    }

    @Test
    void isSixMonthsPriceValidEmpty() {
        final String price = "";
        assertFalse(editionValidator.isSixMonthsPriceValid(price));
    }

    @Test
    void isTwelveMonthsPriceValidPositive() {
        final String price = "54.81";
        assertTrue(editionValidator.isTwelveMonthsPriceValid(price));
    }

    @Test
    void isTwelvePriceValidNegative() {
        final String price = "as54";
        assertFalse(editionValidator.isTwelveMonthsPriceValid(price));
    }

    @Test
    void isTwelveMonthsPriceValidEmpty() {
        final String price = "";
        assertFalse(editionValidator.isTwelveMonthsPriceValid(price));
    }

    @Test
    void isCategoryValid() {
        final String cat = "Letter";
        assertFalse(editionValidator.isCategoryValid(cat));
    }

    @Test
    void validateAll() {
        final String name = "The-New.York, & Times 5!?";
        final String threePrice = "54.81";
        final String sixPrice = "54.81";
        final String twelvePrice = "54.81";
        final String cat = "Book";
        assertTrue(editionValidator.validateAll(name, threePrice, sixPrice, twelvePrice, cat));
    }
}