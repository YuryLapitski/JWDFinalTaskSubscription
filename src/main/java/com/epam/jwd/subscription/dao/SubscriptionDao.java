package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Subscription;

import java.util.List;

public interface SubscriptionDao extends EntityDao<Subscription> {

    List<Subscription> findIdByAll (Long userId, Long addressId, Long editionId, Long termId,
                                    Long priceId, Long statusId);

    void updateStatus (Long statusId, Long subscriptionId);

    static SubscriptionDao instance() {
        return MethodSubscriptionDao.getInstance();

    }
}
