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
    private static final String ERROR_SIGNUP_ATTRIBUTE = "errorSignUpMessage";
    private static final String ERROR_SIGNUP_MESSAGE = "Invalid login or password";
    private static final String ACCOUNT_SESSION_ATTRIBUTE_NAME = "account";
    private static final String LOGIN_REQUEST_PARAM_NAME = "signup";
    private static final String PASSWORD_REQUEST_PARAM_NAME = "password";
    private static final String PASSWORD_COPY_REQUEST_PARAM_NAME = "passwordCopy";
    private static final String ERROR_PASSWORD_MISMATCH_ATTRIBUTE = "errorPasswordMismatchMessage";
    private static final String ERROR_PASSWORD_MISMATCH_MESSAGE = "Password mismatch";

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
        if (request.sessionExists() && request.retrieveFromSession(ACCOUNT_SESSION_ATTRIBUTE_NAME).isPresent()) {
            //todo: error - account alredy logged in
            return null;
        }
        final String login = request.getParameter(LOGIN_REQUEST_PARAM_NAME);
        final String password = request.getParameter(PASSWORD_REQUEST_PARAM_NAME);
        final String passwordCopy = request.getParameter(PASSWORD_COPY_REQUEST_PARAM_NAME);
        if (AccountValidator.getInstance().validateAll(login, password) && passwordCopy.equals(password)) {
            Account newAccount = new Account(login, password, USER_ROLE_ID);
            accountService.create(newAccount);
            return requestFactory.createRedirectResponse(propertyContext.get(INDEX_PAGE));
        } else {
            if (!AccountValidator.getInstance().validateAll(login, password)) {
                request.addAttributeToJsp(ERROR_SIGNUP_ATTRIBUTE, ERROR_SIGNUP_MESSAGE);
            } else {
                request.addAttributeToJsp(ERROR_PASSWORD_MISMATCH_ATTRIBUTE, ERROR_PASSWORD_MISMATCH_MESSAGE);
            }
            return requestFactory.createForwardResponse(propertyContext.get(SIGNUP_PAGE));
        }
    }
}
