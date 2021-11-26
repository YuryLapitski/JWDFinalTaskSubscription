package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.EditionDao;
import com.epam.jwd.subscription.entity.Edition;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EditionService implements EntityService<Edition> {

    private final EditionDao editionDao;

    public EditionService(EditionDao editionDao) {
        this.editionDao = editionDao;
    }

    @Override
    public List<Edition> findAll() {
        return editionDao.read();
    }

    @Override
    public List<Edition> findAllById(String id) {
        return Collections.emptyList();
    }

    @Override
    public Optional<Edition> create(Edition entity) {
        return Optional.empty();
    }
}
