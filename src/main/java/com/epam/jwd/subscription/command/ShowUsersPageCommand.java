package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.entity.User;
import com.epam.jwd.subscription.service.EntityService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ShowUsersPageCommand implements Command {

    private static final String USERS_ATTRIBUTE_NAME = "users";

    private static ShowUsersPageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private final static CommandResponse FORWARD_TO_USERS_PAGE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "WEB-INF/jsp/users.jsp";
        }
    };
    private final EntityService<User> service;

    private ShowUsersPageCommand(EntityService<User> service) {
        this.service = service;
    }

    public static ShowUsersPageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowUsersPageCommand(ServiceFactory.instance().serviceFor(User.class));
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
        request.addAttributeToJsp(USERS_ATTRIBUTE_NAME, users);
        return FORWARD_TO_USERS_PAGE;
    }
}
