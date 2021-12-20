package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.User;

import java.util.Optional;

public interface UserService extends EntityService<User> {

    Optional<User> readUserByAccountId(Long accId);

    Optional<User> findById(Long id);

    void updateByAccountId (String firstName, String lastName,
                            Integer age, String email, Long accountId);
}
