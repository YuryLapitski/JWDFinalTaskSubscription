package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.SubscriptionDao;
import com.epam.jwd.subscription.dao.UserDao;
import com.epam.jwd.subscription.entity.Subscription;

import java.util.List;
import java.util.Optional;

public class SubscriptionService implements EntityService<Subscription> {

    private final SubscriptionDao subscriptionDao;

    public SubscriptionService(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    @Override
    public List<Subscription> findAll() {
        return subscriptionDao.read();
    }

    @Override
    public Optional<Subscription> findById(Long id) {
        return subscriptionDao.read(id);
    }

    @Override
    public Optional<Subscription> create(Subscription entity) {
        return Optional.ofNullable(subscriptionDao.create(entity));
    }
}
