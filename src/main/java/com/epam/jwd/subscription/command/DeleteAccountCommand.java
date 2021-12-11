package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.*;
import com.epam.jwd.subscription.service.*;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class DeleteAccountCommand implements Command {

    private static final String ACCOUNTS_PAGE = "page.accounts";
    private static final String ACC_ID_REQUEST_PARAM_NAME = "accId";
    private static final String JSP_ACCOUNTS_ATTRIBUTE_NAME = "accounts";

    private static DeleteAccountCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final AccountService accountService;
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private DeleteAccountCommand(AccountService accountService,
                                 UserService userService,
                                 SubscriptionService subscriptionService,
                                 RequestFactory requestFactory,
                                 PropertyContext propertyContext) {
        this.accountService = accountService;
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static DeleteAccountCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new DeleteAccountCommand(ServiceFactory.instance().accountService(),
                            ServiceFactory.instance().userService(),
                            ServiceFactory.instance().subscriptionService(),
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
        final Long accId = Long.parseLong(request.getParameter(ACC_ID_REQUEST_PARAM_NAME));
        final Optional<User> optionalUser = userService.readUserByAccountId(accId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            final List<Subscription> subscriptions = subscriptionService.findByUserId(user.getId());
            for (Subscription subscription : subscriptions) {
                final Long subId = subscription.getId();
                subscriptionService.delete(subId);
            }
            userService.delete(user.getId());
        }
        accountService.delete(accId);
        final List<Account> accounts = accountService.findAll();
        request.addAttributeToJsp(JSP_ACCOUNTS_ATTRIBUTE_NAME, accounts);
        return requestFactory.createForwardResponse(propertyContext.get(ACCOUNTS_PAGE));
    }

}
