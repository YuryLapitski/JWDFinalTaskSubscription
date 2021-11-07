package com.epam.jwd.subscription.controller;

public enum PagePaths {
    INDEX("/"),
    MAIN("/WEB-INF/jsp/main.jsp"),
    EDITIONS("/WEB-INF/jsp/editions.jsp"),
    LOGIN("/WEB-INF/jsp/login.jsp"),
    USERS("/WEB-INF/jsp/users.jsp"),
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
