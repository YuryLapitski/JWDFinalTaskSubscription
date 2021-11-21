package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.entity.Entity;
import com.epam.jwd.subscription.entity.User;

public interface ServiceFactory {

    <T extends Entity> EntityService<T> serviceFor(Class<T> entityClass);

    default AccountService accountService() {
        return (AccountService) serviceFor(Account.class);
    }

    default UserService userService() {
        return (UserService) serviceFor(User.class);
    }

    static SimpleServiceFactory instance() {
        return SimpleServiceFactory.getInstance();
    }
}
