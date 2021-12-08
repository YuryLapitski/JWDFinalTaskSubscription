package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import java.util.concurrent.locks.ReentrantLock;

public class ChangeLanguageCommand implements Command {

    private static final String INDEX_PAGE = "page.index";
    private static final String LANG_REQUEST_PARAM_NAME = "lang";


    private static ChangeLanguageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ChangeLanguageCommand(RequestFactory requestFactory, PropertyContext propertyContext) {
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ChangeLanguageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ChangeLanguageCommand(RequestFactory.getInstance(), PropertyContext.getInstance());
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final String language = request.getParameter(LANG_REQUEST_PARAM_NAME);
        switch (language) {
            case "English":
                request.addToSession(LANG_REQUEST_PARAM_NAME, "en_US");
                break;
            case "Russian":
                request.addToSession(LANG_REQUEST_PARAM_NAME, "ru_RU");
                break;
        }
        return requestFactory.createRedirectResponse(propertyContext.get(INDEX_PAGE));
    }
}
