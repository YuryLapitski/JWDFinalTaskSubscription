package com.epam.jwd.subscription.validator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressValidatorTest {

    AddressValidator addressValidator;

    @BeforeAll
    void init() {
        addressValidator = AddressValidator.getInstance();
    }

    @Test
    void isCityValidPositive() {
        final String city = "Minsk";
        assertTrue(addressValidator.isCityValid(city));
    }

    @Test
    void isCityValidNegative() {
        final String city = "123Minsk";
        assertFalse(addressValidator.isCityValid(city));
    }

    @Test
    void isStreetValidPositive() {
        final String street = "ul.Gromova";
        assertTrue(addressValidator.isStreetValid(street));
    }

    @Test
    void isStreetValidNegative() {
        final String street = "ul.&Gromova";
        assertFalse(addressValidator.isStreetValid(street));
    }

    @Test
    void isHouseValidPositive() {
        final String house = "53/1";
        assertTrue(addressValidator.isHouseValid(house));
    }

    @Test
    void isHouseValidNegative() {
        final String house = "";
        assertFalse(addressValidator.isHouseValid(house));
    }

    @Test
    void validateAllPositive() {
        final String city = "Minsk";
        final String street = "ul.Gromova";
        final String house = "53/1";
        assertTrue(addressValidator.validateAll(city, street, house));
    }

    @Test
    void validateAllNegative() {
        final String city = "123Minsk";
        final String street = "ul.&Gromova";
        final String house = "";
        assertFalse(addressValidator.validateAll(city, street, house));
    }
}