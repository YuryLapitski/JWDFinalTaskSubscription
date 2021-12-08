package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Edition;

import java.util.List;
import java.util.Optional;

public interface EditionDao extends EntityDao<Edition> {

    List<Edition> findByName(String name);

    Optional<Edition> findEditionById(Long id);

    void updateByEditionId(String name, Long catId, Long editionId);

    static EditionDao instance() {
        return MethodEditionDao.getInstance();
    }
}
