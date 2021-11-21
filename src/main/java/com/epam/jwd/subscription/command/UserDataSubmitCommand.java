package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.entity.User;
import com.epam.jwd.subscription.service.ServiceFactory;
import com.epam.jwd.subscription.service.UserService;
import com.epam.jwd.subscription.validator.UserDataValidator;

import java.util.concurrent.locks.ReentrantLock;

public class UserDataSubmitCommand implements Command {

    private static UserDataSubmitCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final UserService userService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private static final String INDEX_PAGE = "page.index";
    private static final String USER_DATA_PAGE = "page.user_data";
    private static final String ERROR_USER_DATA_ATRIBUTE = "errorSignUpMessage";
    private static final String ERROR_USER_DATA_MESSAGE = "Invalid user data";
    private static final String ERROR_SESSION_DOES_NOT_EXIST_MESSAGE = "Please log in";
    private static final String ACCOUNT_SESSION_ATTRIBUTE_NAME = "account";
    private static final String FIRST_NAME_REQUEST_PARAM_NAME = "first_name";
    private static final String LAST_NAME_REQUEST_PARAM_NAME = "last_name";
    private static final String AGE_REQUEST_PARAM_NAME = "age";
    private static final String EMAIL_REQUEST_PARAM_NAME = "email";

    private UserDataSubmitCommand(UserService userService, RequestFactory requestFactory,
                          PropertyContext propertyContext) {
        this.userService = userService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static UserDataSubmitCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new UserDataSubmitCommand(ServiceFactory.instance().userService(),
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
        final String firstName = request.getParameter(FIRST_NAME_REQUEST_PARAM_NAME);
        final String lastName = request.getParameter(LAST_NAME_REQUEST_PARAM_NAME);
        final int age = Integer.parseInt(request.getParameter(AGE_REQUEST_PARAM_NAME));
        final String email = request.getParameter(EMAIL_REQUEST_PARAM_NAME);
        final Long accId = getAccountId(request);
        if (UserDataValidator.getInstance().validateAll(firstName, lastName, age, email)) {
            User newUser = new User(firstName, lastName, age, email, accId);
            userService.create(newUser);
            return requestFactory.createRedirectResponse(propertyContext.get(INDEX_PAGE));
        } else {
            request.addAttributeToJsp(ERROR_USER_DATA_ATRIBUTE, ERROR_USER_DATA_MESSAGE);
            return requestFactory.createForwardResponse(propertyContext.get(USER_DATA_PAGE));
        }
    }

    private Long getAccountId (CommandRequest request) {
        if (request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME).isPresent()) {
            Account account = (Account) request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME).get();
            return account.getId();
        } else {
           request.addAttributeToJsp(ERROR_USER_DATA_ATRIBUTE, ERROR_SESSION_DOES_NOT_EXIST_MESSAGE);
           requestFactory.createForwardResponse(propertyContext.get(USER_DATA_PAGE));
           return null;
        }
    }
}
