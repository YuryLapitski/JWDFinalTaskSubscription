package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;

import java.util.concurrent.locks.ReentrantLock;

public class LogoutCommand implements Command {
    private static final String ACCOUNT_SESSION_ATTRIBUTE_NAME = "account";
    private static final String INDEX_PAGE = "page.index";

    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private static LogoutCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private LogoutCommand(RequestFactory requestFactory, PropertyContext propertyContext) {
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static LogoutCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new LogoutCommand(RequestFactory.getInstance(), PropertyContext.getInstance());
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        if (noLoggedInUserPresent(request)) {
            //todo: error - no user found cannot logout
            return null;
        }
        request.clearSession();
        return requestFactory.createRedirectResponse(propertyContext.get(INDEX_PAGE));
    }

    private boolean noLoggedInUserPresent(CommandRequest request) {
        return !request.sessionExists() || (
                request.sessionExists()
                        && !request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME)
                        .isPresent()
        );
    }
}
