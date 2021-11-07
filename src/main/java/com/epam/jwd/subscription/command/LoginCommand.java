package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.service.AccountService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class LoginCommand implements Command {

    private static final String INDEX_PAGE = "page.index";
    private static final String LOGIN_PAGE = "page.login";
    private static final String ERROR_LOGIN_PASS_ATRIBUTE = "errorLoginPassMessage";
    private static final String ERROR_LOGIN_PASS_MESSAGE = "Invalid login or password";
    private static final String ACCOUNT_SESSION_ATRIBUTE_NAME = "account";
    private static final String LOGIN_REQUEST_PARAM_NAME = "login";
    private static final String PASSWORD_REQUEST_PARAM_NAME = "password";

    private static LoginCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final AccountService accountService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private LoginCommand(AccountService accountService, RequestFactory requestFactory,
                         PropertyContext propertyContext) {
        this.accountService = accountService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static LoginCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new LoginCommand(ServiceFactory.instance().accountService(),
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
        final String login = request.getParameter(LOGIN_REQUEST_PARAM_NAME);
        final String password = request.getParameter(PASSWORD_REQUEST_PARAM_NAME);
        final Optional<Account> account = accountService.authenticate(login, password);
        if (!account.isPresent()) {
            request.addAttributeToJsp(ERROR_LOGIN_PASS_ATRIBUTE, ERROR_LOGIN_PASS_MESSAGE);
            requestFactory.createForwardResponse(propertyContext.get(LOGIN_PAGE));

            return null;
        }
        request.clearSession();
        request.createSession();
        request.addToSession(ACCOUNT_SESSION_ATRIBUTE_NAME, account.get());
        return requestFactory.createRedirectResponse(propertyContext.get(INDEX_PAGE));
    }
}
