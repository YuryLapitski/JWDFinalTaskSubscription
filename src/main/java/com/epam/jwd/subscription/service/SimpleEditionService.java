package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.EditionDao;
import com.epam.jwd.subscription.entity.Edition;

import java.util.List;
import java.util.Optional;

public class SimpleEditionService implements EditionService {

    private final EditionDao editionDao;

    public SimpleEditionService(EditionDao editionDao) {
        this.editionDao = editionDao;
    }

    @Override
    public List<Edition> findAll() {
        return editionDao.read();
    }

    @Override
    public Optional<Edition> findById(Long id) {
        return editionDao.findEditionById(id);
    }

    @Override
    public Optional<Edition> create(Edition entity) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return editionDao.delete(id);
    }

    @Override
    public void updateByEditionId(String name, Long catId, Long editionId) {
        editionDao.updateByEditionId(name, catId, editionId);
    }
}
