package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.UserDao;
import com.epam.jwd.subscription.entity.User;

import java.util.List;
import java.util.Optional;

public class SimpleUserService implements UserService {

    private final UserDao userDao;

    public SimpleUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAll() {
        return userDao.read();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userDao.readUserByAccountId(id);
    }

    @Override
    public Optional<User> create(User entity) {
        return Optional.ofNullable(userDao.create(entity));
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    @Override
    public Optional<User> readUserByAccountId(Long accId) {
        return userDao.readUserByAccountId(accId);
    }

    @Override
    public void updateByAccountId(String firstName, String lastName, Integer age, String email, Long accountId) {
        userDao.updateByAccountId(firstName, lastName, age, email, accountId);
    }
}
