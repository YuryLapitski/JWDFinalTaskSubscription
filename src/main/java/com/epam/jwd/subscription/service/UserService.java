package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.UserDao;
import com.epam.jwd.subscription.entity.User;

import java.util.List;
import java.util.Optional;

public class UserService implements EntityService<User> {

    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAll() {
        return userDao.read();
    }

    @Override
    public Optional<User> create(User entity) {
        return Optional.empty();
    }
}
