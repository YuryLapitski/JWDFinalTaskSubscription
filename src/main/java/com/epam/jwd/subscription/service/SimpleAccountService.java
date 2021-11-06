package com.epam.jwd.subscription.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.epam.jwd.subscription.dao.AccountDao;
import com.epam.jwd.subscription.entity.Account;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class SimpleAccountService implements AccountService {

    private static final byte[] DUMMY_PASSWORD = "password".getBytes(StandardCharsets.UTF_8);

    private final AccountDao accountDao;
//    private final BCrypt.Hasher hasher;
//    private final BCrypt.Verifyer verifier;


    public SimpleAccountService(AccountDao accountDao) {
//        this.hasher = hasher;
//        this.verifier = verifier;
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> findAll() {
        return accountDao.read();
    }

    @Override
    public Optional<Account> authenticate(String login, String password) {
        final Optional<Account> readAccount = accountDao.readAccountByLogin(login);
        return readAccount.filter(account -> account.getPassword().equals(password));



//        if (login == null || password == null) {
//            return Optional.empty();
//        }
//        final byte[] enteredPassword = password.getBytes(StandardCharsets.UTF_8);
//        final Optional<Account> readAccount = accountDao.readAccountByLogin(login);
//        if (readAccount.isPresent()) {
//            final byte[] hashedPassword = readAccount.get()
//                    .getPassword()
//                    .getBytes(StandardCharsets.UTF_8);
//            return verifier.verify(enteredPassword, hashedPassword).verified
//                    ? readAccount
//                    : Optional.empty();
//        } else {
//            protectFromTimingAttack(enteredPassword);
//            return Optional.empty();
//        }
    }

//    private void protectFromTimingAttack(byte[] enteredPassword) {
//        verifier.verify(enteredPassword, DUMMY_PASSWORD);
//    }
}
