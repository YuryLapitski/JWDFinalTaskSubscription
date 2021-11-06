package com.epam.jwd.subscription.controller;

import com.epam.jwd.subscription.command.CommandRequest;
import com.epam.jwd.subscription.command.CommandResponse;

import javax.servlet.http.HttpServletRequest;

public interface RequestFactory {

    CommandRequest createRequest(HttpServletRequest request);

    CommandResponse createForwardResponse(String path);

    CommandResponse createRedirectResponse (String path);

    static RequestFactory getInstance() {
        return SimpleRequestFactory.getInstance();
    }
}
