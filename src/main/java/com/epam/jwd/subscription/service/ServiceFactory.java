package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.entity.Entity;

public interface ServiceFactory {

    <T extends Entity> EntityService<T> serviceFor(Class<T> entityClass);

    default AccountService accountService() {
        return (AccountService) serviceFor(Account.class);
    }

    static SimpleServiceFactory instance() {
        return SimpleServiceFactory.getInstance();
    }
}
