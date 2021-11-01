package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.AccountDao;
import com.epam.jwd.subscription.entity.Account;

import java.util.List;

public class AccountService implements EntityService<Account> {

    private final AccountDao accountDao;

    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAll() {
        return accountDao.read();
    }
}
