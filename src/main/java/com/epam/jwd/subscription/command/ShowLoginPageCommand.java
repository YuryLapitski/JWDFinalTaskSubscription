package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.RequestFactory;

import java.util.concurrent.locks.ReentrantLock;

public class ShowLoginPageCommand implements Command {

    private static final String LOGIN_JSP_PATH = "WEB-INF/jsp/login.jsp";

    private static ShowLoginPageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final RequestFactory requestFactory;

    private ShowLoginPageCommand(RequestFactory requestFactory) {
        this.requestFactory = requestFactory;
    }

    public static ShowLoginPageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowLoginPageCommand(RequestFactory.getInstance());
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(LOGIN_JSP_PATH);
    }
}
