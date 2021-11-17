package com.epam.jwd.subscription.validator;

import com.epam.jwd.subscription.dao.AccountDao;
import com.epam.jwd.subscription.entity.Account;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountValidator {

    private static final Pattern LOGIN_PATTERN = Pattern.compile("^[A-za-z0-9_-]{2,30}$");

    public boolean isLoginValid(String login) {
        if (login == null) {
            return false;
        }
        if (login.isEmpty()) {
            return false;
        }
        Matcher matcher = LOGIN_PATTERN.matcher(login.replaceAll("\\s+", ""));
        if (matcher.matches()) {
            Optional<Account> possibleCollision = AccountDao.instance().readAccountByLogin(login);
            return !possibleCollision.isPresent();
        } else {
            return false;
        }
    }

    public boolean isPasswordCorrect(String password){
        if (password==null){
            return false;
        }
        return !password.isEmpty();
    }

    public boolean validateAll(String login, String password){
        return isLoginValid(login)
                & isPasswordCorrect(password);
    }
}
