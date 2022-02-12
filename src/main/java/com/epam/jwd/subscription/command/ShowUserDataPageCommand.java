package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.entity.User;
import com.epam.jwd.subscription.service.ServiceFactory;
import com.epam.jwd.subscription.service.UserService;

import java.util.concurrent.locks.ReentrantLock;

public class ShowUserDataPageCommand implements Command {

    private static final String USER_DATA_PAGE = "page.user_data";
    private static final String ACCOUNT_SESSION_ATTRIBUTE_NAME = "account";
    private static final String JSP_USER_ATTRIBUTE_NAME = "user";

    private static ShowUserDataPageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final UserService userService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowUserDataPageCommand(UserService userService, RequestFactory requestFactory,
                                    PropertyContext propertyContext) {
        this.userService = userService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowUserDataPageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowUserDataPageCommand(ServiceFactory.getInstance().userService(),
                            RequestFactory.getInstance(),
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
        if (request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME).isPresent()) {
            Account account = (Account) request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME).get();
            if (userService.findById(account.getId()).isPresent()) {
                User user = userService.findById(account.getId()).get();
                request.addAttributeToJsp(JSP_USER_ATTRIBUTE_NAME, user);
            }
        }
        return requestFactory.createForwardResponse(propertyContext.get(USER_DATA_PAGE));
    }
}
