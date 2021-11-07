package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.service.EntityService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ShowAccountsPageCommand implements Command {

    private static final String JSP_ACCOUNTS_ATTRIBUTE_NAME = "accounts";
    private static final String ACCOUNTS_PAGE = "page.accounts";

    private static ShowAccountsPageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private final EntityService<Account> service;
    private final RequestFactory requestFactory;
    private final PropertyContext propertyContext;

    private ShowAccountsPageCommand(EntityService<Account> service, RequestFactory requestFactory,
                                    PropertyContext propertyContext) {
        this.service = service;
        this.requestFactory = requestFactory;
        this.propertyContext = propertyContext;
    }

    public static ShowAccountsPageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowAccountsPageCommand(ServiceFactory.instance().serviceFor(Account.class),
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
        final List<Account> accounts = service.findAll();
        request.addAttributeToJsp(JSP_ACCOUNTS_ATTRIBUTE_NAME, accounts);
        return requestFactory.createForwardResponse(propertyContext.get(ACCOUNTS_PAGE));
    }
}
