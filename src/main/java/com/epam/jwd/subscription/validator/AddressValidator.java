package com.epam.jwd.subscription.validator;

import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddressValidator {
    private static final Pattern CITY_PATTERN = Pattern.compile("^[A-za-z]{2,50}$");
    private static final Pattern STREET_PATTERN = Pattern.compile("^[A-za-z0-9.-]{2,100}$");

    private static AddressValidator instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private AddressValidator() {
    }

    public static AddressValidator getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new AddressValidator();
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    public boolean isCityValid(String city) {
        if (city == null || city.isEmpty()) {
            return false;
        }
        Matcher matcher = CITY_PATTERN.matcher(city.replaceAll("\\s+", ""));
        return matcher.matches();
    }

    public boolean isStreetValid(String street) {
        if (street == null || street.isEmpty()) {
            return false;
        }
        Matcher matcher = STREET_PATTERN.matcher(street.replaceAll("\\s+", ""));
        return matcher.matches();
    }

    public boolean isHouseValid(String house) {
        return house != null && !house.isEmpty();
    }

    public boolean validateAll(String city, String street, String house){
        return isCityValid(city)
                && isStreetValid(street)
                && isHouseValid(house);
    }
}
