package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Account;

import java.util.Optional;

public interface AccountDao extends EntityDao<Account> {

    Optional<Account> readAccountByLogin(String login);

    static AccountDao instance() {
        return MethodAccountDao.getInstance();
    }

}
