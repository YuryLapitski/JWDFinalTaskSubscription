package com.epam.jwd.subscription.controller;

public enum PagePaths {
    INDEX("/"),
    MAIN("/WEB-INF/jsp/main.jsp"),
    ACCOUNTS("/WEB-INF/jsp/accounts.jsp"),
    EDITIONS("/WEB-INF/jsp/editions.jsp"),
    LOGIN("/WEB-INF/jsp/login.jsp"),
    SIGNUP("/WEB-INF/jsp/signup.jsp"),
    USERS("/WEB-INF/jsp/users.jsp"),
    USER_DATA("/WEB-INF/jsp/user_data.jsp"),
    ADDRESS("/WEB-INF/jsp/address.jsp"),
    TERM("/WEB-INF/jsp/term.jsp"),
    SUBSCRIBE("/WEB-INF/jsp/subscribe.jsp"),
    SHOPPING_CARD("/WEB-INF/jsp/shopping_card.jsp"),
    ERROR("/WEB-INF/jsp/error.jsp");

    private final String path;

    PagePaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public static PagePaths of(String name) {
        for (PagePaths page : values()) {
            if (page.name().equalsIgnoreCase(name)) {
                return page;
            }
        }
        return MAIN;
    }
}
