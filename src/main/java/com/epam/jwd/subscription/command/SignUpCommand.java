package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.service.AccountService;
import com.epam.jwd.subscription.service.ServiceFactory;
import com.epam.jwd.subscription.validator.AccountValidator;

import java.util.concurrent.locks.ReentrantLock;

public class SignUpCommand implements Command {

    private static final String INDEX_PAGE = "page.index";
    private static final String SIGNUP_PAGE = "page.signup";
    private static final String ERROR_SIGNUP_ATRIBUTE = "errorSignUpMessage";
    private static final String ERROR_SIGNUP_MESSAGE = "Invalid login or password";
    private static final String ACCOUNT_SESSION_ATRIBUTE_NAME = "account";
    private static final String LOGIN_REQUEST_PARAM_NAME = "signup";
    private static final String PASSWORD_REQUEST_PARAM_NAME = "password";
    private static final Integer USER_ROLE_ID = 1;

    private static SignUpCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final AccountService accountService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private SignUpCommand(AccountService accountService, RequestFactory requestFactory,
                          PropertyContext propertyContext) {
        this.accountService = accountService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static SignUpCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new SignUpCommand(ServiceFactory.instance().accountService(),
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
        if (request.sessionExists() && request.retrieveFromSession(ACCOUNT_SESSION_ATRIBUTE_NAME).isPresent()) {
            //todo: error - account alredy logged in
            return null;
        }
        AccountValidator validator = new AccountValidator();
        final String login = request.getParameter(LOGIN_REQUEST_PARAM_NAME);
        final String password = request.getParameter(PASSWORD_REQUEST_PARAM_NAME);
        if (validator.validateAll(login, password)) {
            Account newAccount = new Account(login, password, USER_ROLE_ID);
            accountService.create(newAccount);
            return requestFactory.createRedirectResponse(propertyContext.get(INDEX_PAGE));
        } else {
            request.addAttributeToJsp(ERROR_SIGNUP_ATRIBUTE, ERROR_SIGNUP_MESSAGE);
            return requestFactory.createForwardResponse(propertyContext.get(SIGNUP_PAGE));
        }
    }
}
