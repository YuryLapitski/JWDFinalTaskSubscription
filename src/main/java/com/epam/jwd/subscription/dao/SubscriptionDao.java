package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Subscription;

public interface SubscriptionDao extends EntityDao<Subscription> {

    static SubscriptionDao instance() {
        return MethodSubscriptionDao.getInstance();
    }
}
