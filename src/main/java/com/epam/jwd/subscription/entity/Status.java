package com.epam.jwd.subscription.entity;

import java.util.Arrays;
import java.util.List;

public enum Status {

    ORDERING,
    ACTIVE,
    FINISHED;

    private static final List<Status> ALL_AVAILABLE_STATUSES = Arrays.asList(values());

    public static List<Status> valuesAsList() {
        return ALL_AVAILABLE_STATUSES;
    }

    public static Status of(String name) {
        for (Status status : values()) {
            if (status.name().equalsIgnoreCase(name)) {
                return status;
            }
        }
        return null;
    }
}
