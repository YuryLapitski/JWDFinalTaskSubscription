package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.SubscriptionDao;
import com.epam.jwd.subscription.entity.Subscription;

import java.util.List;
import java.util.Optional;

public class SimpleSubscriptionService implements SubscriptionService{

    private final SubscriptionDao subscriptionDao;

    public SimpleSubscriptionService(SubscriptionDao subscriptionDao) {
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

    @Override
    public List<Subscription> findIdByAll(Long userId, Long addressId, Long editionId, Long termId, Long priceId, Long statusId) {
        return subscriptionDao.findIdByAll(userId, addressId,editionId,termId,priceId,statusId);
    }

    @Override
    public void updateStatus(Long statusId, Long subscriptionId) {
        subscriptionDao.updateStatus(statusId, subscriptionId);
    }
}
