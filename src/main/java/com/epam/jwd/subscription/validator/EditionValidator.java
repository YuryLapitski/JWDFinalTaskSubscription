package com.epam.jwd.subscription.validator;

import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditionValidator {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-za-z0-9.,!?&-]{2,100}$");
    private static final Pattern PRICE_PATTERN = Pattern.compile("^[0-9]{1,4}[.,]?[0-9]+$");

    private static EditionValidator instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private EditionValidator() {
    }

    public static EditionValidator getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new EditionValidator();
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    public boolean isNameValid(String name) {
        if (name == null || name.isEmpty()) {
            return false;
        }
        Matcher matcher = NAME_PATTERN.matcher(name.replaceAll("\\s+", ""));
        return matcher.matches();
    }

    public boolean isThreeMonthsPriceValid(String threeMonthsPrice) {
        if (threeMonthsPrice == null || threeMonthsPrice.isEmpty()) {
            return false;
        }
        Matcher matcher = PRICE_PATTERN.matcher(threeMonthsPrice.replaceAll("\\s+", ""));
        return matcher.matches();
    }

    public boolean isSixMonthsPriceValid(String sixMonthsPrice) {
        if (sixMonthsPrice == null || sixMonthsPrice.isEmpty()) {
            return false;
        }
        Matcher matcher = PRICE_PATTERN.matcher(sixMonthsPrice.replaceAll("\\s+", ""));
        return matcher.matches();
    }

    public boolean isTwelveMonthsPriceValid(String twelveMonthsPrice) {
        if (twelveMonthsPrice == null || twelveMonthsPrice.isEmpty()) {
            return false;
        }
        Matcher matcher = PRICE_PATTERN.matcher(twelveMonthsPrice.replaceAll("\\s+", ""));
        return matcher.matches();
    }

    public boolean isCategoryValid(String category) {
        return category.equals("Newspaper") || category.equals("Magazine") || category.equals("Book");
    }

    public boolean validateAll(String name, String threeMonthsPrice, String sixMonthsPrice,
                               String twelveMonthsPrice, String category){
        return isNameValid(name)
                && isThreeMonthsPriceValid(threeMonthsPrice)
                && isSixMonthsPriceValid(sixMonthsPrice)
                && isTwelveMonthsPriceValid(twelveMonthsPrice)
                && isCategoryValid(category);
    }
}
