package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;

import java.util.concurrent.locks.ReentrantLock;

public class ShowAddEditionPageCommand implements Command {

    private static final String ADD_EDITIONS_PAGE = "page.add_edition";

    private static ShowAddEditionPageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowAddEditionPageCommand(RequestFactory requestFactory,
                                         PropertyContext propertyContext) {
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowAddEditionPageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowAddEditionPageCommand(RequestFactory.getInstance(),
                            PropertyContext.getInstance());
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        return requestFactory.createForwardResponse(propertyContext.get(ADD_EDITIONS_PAGE));
    }
}
