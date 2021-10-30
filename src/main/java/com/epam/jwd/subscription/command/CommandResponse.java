package com.epam.jwd.subscription.command;

public interface CommandResponse {

    boolean isRedirect();

    String getPath();

}
