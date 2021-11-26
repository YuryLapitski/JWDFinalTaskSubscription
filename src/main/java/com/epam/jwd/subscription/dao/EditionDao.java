package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Edition;

import java.util.List;
import java.util.Optional;

public interface EditionDao extends EntityDao<Edition> {

    List<Edition> findByName(String name);

    Optional<Long> findUserIdByEditionId(Long id);

    static EditionDao instance() {
        return MethodEditionDao.getInstance();
    }

}
