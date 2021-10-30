package com.epam.jwd.subscription.entity;

import java.util.Arrays;
import java.util.List;

public enum Role {
    USER,
    ADMIN,
    UNAUTHORIZED;

    private static final List<Role> ALL_AVAILABLE_ROLES = Arrays.asList(values());

    public static List<Role> valuesAsList() {
        return ALL_AVAILABLE_ROLES;
    }

    public static com.epam.jwd.subscription.entity.Role of(String name) {
        for (com.epam.jwd.subscription.entity.Role role : values()) {
            if (role.name().equalsIgnoreCase(name)) {
                return role;
            }
        }
        return USER;
    }

}
