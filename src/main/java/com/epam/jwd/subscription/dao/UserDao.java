package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.User;

import java.util.Optional;

public interface UserDao extends EntityDao<User> {

    Optional<User> readUserByEmail(String email);

    Optional<User> readUserByAccountId(Long accId);

    void updateByAccountId (String firstName, String lastName,
                            Integer age, String email, Long accountId);

    static UserDao instance() {
        return MethodUserDao.getInstance();
    }
}
