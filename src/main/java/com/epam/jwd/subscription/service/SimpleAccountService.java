package com.epam.jwd.subscription.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.epam.jwd.subscription.dao.AccountDao;
import com.epam.jwd.subscription.entity.Account;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static at.favre.lib.crypto.bcrypt.BCrypt.MIN_COST;

public class SimpleAccountService implements AccountService {

    private static final byte[] DUMMY_PASSWORD = "password".getBytes(StandardCharsets.UTF_8);

    private final AccountDao accountDao;
    private final BCrypt.Hasher hasher;
    private final BCrypt.Verifyer verifier;


    public SimpleAccountService(AccountDao accountDao, BCrypt.Hasher hasher, BCrypt.Verifyer verifier) {
        this.accountDao = accountDao;
        this.hasher = hasher;
        this.verifier = verifier;
    }

    @Override
    public List<Account> findAll() {
        return accountDao.read();
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountDao.read(id);
    }

    @Override
    public Optional<Account> create(Account account) {
        final char[] rawPassword = account.getPassword().toCharArray();
        final String hashedPassword = hasher.hashToString(MIN_COST, rawPassword);
        return Optional.ofNullable(accountDao.create(account.withPassword(hashedPassword)));
    }

    @Override
    public boolean delete(Long id) {
        return accountDao.delete(id);
    }

    @Override
    public Optional<Account> authenticate(String login, String password) {
        if (login == null || password == null) {
            return Optional.empty();
        }
        final byte[] enteredPassword = password.getBytes(StandardCharsets.UTF_8);
        final Optional<Account> readAccount = accountDao.readAccountByLogin(login);
        if (readAccount.isPresent()) {
            final byte[] hashedPassword = readAccount.get()
                    .getPassword()
                    .getBytes(StandardCharsets.UTF_8);
            return verifier.verify(enteredPassword, hashedPassword).verified
                    ? readAccount
                    : Optional.empty();
        } else {
            protectFromTimingAttack(enteredPassword);
            return Optional.empty();
        }
    }

    @Override
    public Optional<Account> readAccountByLogin(String login) {
        return accountDao.readAccountByLogin(login);
    }

    private void protectFromTimingAttack(byte[] enteredPassword) {
        verifier.verify(enteredPassword, DUMMY_PASSWORD);
    }
}
