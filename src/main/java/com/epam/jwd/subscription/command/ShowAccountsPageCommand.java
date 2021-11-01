package com.epam.jwd.subscription.command;

import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.entity.Edition;
import com.epam.jwd.subscription.service.EntityService;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ShowAccountsPageCommand implements Command {

    private static final String ACCOUNTS_ATTRIBUTE_NAME = "accounts";

    private static ShowAccountsPageCommand instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();
    private final static CommandResponse FORWARD_TO_ACCOUNTS_PAGE = new CommandResponse() {
        @Override
        public boolean isRedirect() {
            return false;
        }

        @Override
        public String getPath() {
            return "WEB-INF/jsp/accounts.jsp";
        }
    };
    private final EntityService<Account> service;

    private ShowAccountsPageCommand(EntityService<Account> service) {
        this.service = service;
    }

    public static ShowAccountsPageCommand getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ShowAccountsPageCommand(ServiceFactory.instance().serviceFor(Account.class));
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
        request.addAttributeToJsp(ACCOUNTS_ATTRIBUTE_NAME, accounts);
        return FORWARD_TO_ACCOUNTS_PAGE;
    }
}
