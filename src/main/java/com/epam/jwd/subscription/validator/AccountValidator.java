package com.epam.jwd.subscription.validator;

import com.epam.jwd.subscription.command.SignUpCommand;
import com.epam.jwd.subscription.controller.PropertyContext;
import com.epam.jwd.subscription.controller.RequestFactory;
import com.epam.jwd.subscription.dao.AccountDao;
import com.epam.jwd.subscription.entity.Account;
import com.epam.jwd.subscription.service.ServiceFactory;

import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidator {

    private static final Pattern LOGIN_PATTERN = Pattern.compile("^[A-za-z0-9_-]{2,30}$");

    private static AccountValidator instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private AccountValidator() {
    }

    public static AccountValidator getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new AccountValidator();
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    public boolean isLoginValid(String login) {
        if (login == null) {
            return false;
        }
        if (login.isEmpty()) {
            return false;
        }
        Matcher matcher = LOGIN_PATTERN.matcher(login.replaceAll("\\s+", ""));
        return matcher.matches();

//        if (matcher.matches()) {
//            Optional<Account> possibleCollision = AccountDao.instance().readAccountByLogin(login);
//            return !possibleCollision.isPresent();
//        } else {
//            return false;
//        }
    }

    public boolean isPasswordCorrect(String password){
        if (password == null){
            return false;
        }
        return !password.isEmpty();
    }

    public boolean validateAll(String login, String password){
        return isLoginValid(login)
                & isPasswordCorrect(password);
    }
}
