package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.User;
import com.epam.jwd.subscription.service.EntityService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ShowUsersPageCommand implements Command {

    private static final String JSP_USERS_ATTRIBUTE_NAME = "users";
    private static final String USERS_PAGE = "page.users";

    private static ShowUsersPageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final EntityService<User> service;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowUsersPageCommand(EntityService<User> service, RequestFactory requestFactory,
                                 PropertyContext propertyContext) {
        this.service = service;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowUsersPageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowUsersPageCommand(ServiceFactory.getInstance().serviceFor(User.class),
                            RequestFactory.getInstance(), PropertyContext.getInstance());
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    @Override
    public CommandResponse execute(CommandRequest request) {
        final List<User> users = service.findAll();
        request.addAttributeToJsp(JSP_USERS_ATTRIBUTE_NAME, users);
        return requestFactory.createForwardResponse(propertyContext.get(USERS_PAGE));
    }
}
