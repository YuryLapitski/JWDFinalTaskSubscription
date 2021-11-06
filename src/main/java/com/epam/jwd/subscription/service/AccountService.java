package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Account;

import java.util.Optional;

public interface AccountService extends EntityService<Account> {

    Optional<Account> authenticate(String login, String password);

}
