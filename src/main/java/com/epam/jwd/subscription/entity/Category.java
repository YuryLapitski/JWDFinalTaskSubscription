package com.epam.jwd.subscription.entity;

import java.util.Arrays;
import java.util.List;

public enum Category {

    NEWSPAPER,
    MAGAZINE,
    BOOK_SERIES;

    private static final List<Category> ALL_AVAILABLE_CATEGORIES = Arrays.asList(values());

    public static List<Category> valuesAsList() {
        return ALL_AVAILABLE_CATEGORIES;
    }

    public static Category of(String name) {
        for (Category category : values()) {
            if (category.name().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return NEWSPAPER;
    }
}


