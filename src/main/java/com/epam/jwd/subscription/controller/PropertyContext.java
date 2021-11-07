package com.epam.jwd.subscription.controller;

public interface PropertyContext {

    String get(String name);

    static PropertyContext getInstance() {
        return SimplePropertyContext.getInstance();
    }
}
