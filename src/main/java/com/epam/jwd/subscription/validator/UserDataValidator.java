package com.epam.jwd.subscription.validator;

import com.epam.jwd.subscription.dao.UserDao;
import com.epam.jwd.subscription.entity.User;


import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataValidator {

    private static final Pattern FIRST_NAME_PATTERN = Pattern.compile("^[A-za-z]{2,45}$");
    private static final Pattern LAST_NAME_PATTERN = Pattern.compile("^[A-za-z]{2,45}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("(\\w){3,80}\\@(\\w){2,15}\\.(com|ru|by|net|)");

    private static UserDataValidator instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private UserDataValidator() {
    }

    public static UserDataValidator getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new UserDataValidator();
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    public boolean isFirstNameValid(String firstName) {
        if (firstName == null || firstName.isEmpty()) {
            return false;
        }
        Matcher matcher = FIRST_NAME_PATTERN.matcher(firstName.replaceAll("\\s+", ""));
        return matcher.matches();
    }

    public boolean isLastNameValid(String lastName) {
        if (lastName == null || lastName.isEmpty()) {
            return false;
        }
        Matcher matcher = LAST_NAME_PATTERN.matcher(lastName.replaceAll("\\s+", ""));
        return matcher.matches();
    }

    public boolean isAgeValid(int age) {
        return age >= 16 && age <= 100;
    }

    public boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()){
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (matcher.matches()) {
            Optional<User> possibleCollision = UserDao.instance().readUserByEmail(email);
            return !possibleCollision.isPresent();
        } else {
            return false;
        }
    }

    public boolean validateAll(String firstName, String lastName, int age, String email){
        return isFirstNameValid(firstName)
                & isLastNameValid(lastName)
                & isAgeValid(age)
                & isEmailValid(email);
    }
}