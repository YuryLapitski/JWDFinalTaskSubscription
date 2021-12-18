package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Subscription;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface SubscriptionService extends EntityService<Subscription> {

    List<Subscription> findIdByAll (Long userId, Long addressId, Long editionId, Long termId,
                                    Long priceId, Long statusId);

    List<Subscription> findByEditionId (Long editionId);

    List<Subscription> findByUserId (Long userId);

    Optional<Subscription> findByUserIdAndTimestamp (Long userId, Timestamp timestamp);

    void updateStatus(Long statusId, Long subscriptionId);

    void deleteAllSubscriptions (List<Subscription> subscriptions);
}
