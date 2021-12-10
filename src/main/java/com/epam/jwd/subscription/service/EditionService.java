package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Edition;

import java.util.Optional;

public interface EditionService extends EntityService<Edition> {

    void updateByEditionId(String name, Long catId, Long editionId);

    void addEdition(String name, Long catId);

    Optional<Edition> findByName(String name);

}
