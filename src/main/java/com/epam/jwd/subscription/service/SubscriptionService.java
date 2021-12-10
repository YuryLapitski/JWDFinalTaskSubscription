package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Subscription;

import java.util.ArrayList;
import java.util.List;

public interface SubscriptionService extends EntityService<Subscription> {

    List<Subscription> findIdByAll (Long userId, Long addressId, Long editionId, Long termId,
                                    Long priceId, Long statusId);

    List<Subscription> findByEditionId (Long editionId);

    void updateStatus(Long statusId, Long subscriptionId);

    void deleteAllSubscriptions (List<Subscription> subscriptions);
}
