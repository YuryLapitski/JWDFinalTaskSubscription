package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Edition;

public interface EditionService extends EntityService<Edition> {

    void updateByEditionId(String name, Long catId, Long editionId);

}
