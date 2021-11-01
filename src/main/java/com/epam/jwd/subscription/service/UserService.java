package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.UserDao;
import com.epam.jwd.subscription.entity.User;

import java.util.List;

public class UserService implements EntityService<User> {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAll() {
        return userDao.read();
    }
}
