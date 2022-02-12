package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Subscription;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface SubscriptionDao extends EntityDao<Subscription> {

    List<Subscription> findIdByAll (Long userId, Long addressId, Long editionId, Long termId,
                                    Long priceId, Long statusId);

    List<Subscription> findByEditionId (Long editionId);

    List<Subscription> findByUserId (Long userId);

    Optional<Subscription> findByUserIdAndTimestamp (Long userId, Timestamp timestamp);

    void updateStatus (Long statusId, Long subscriptionId);

    static SubscriptionDao getInstance() {
        return MethodSubscriptionDao.getDaoInstance();

    }
}
