package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.StatusDao;
import com.epam.jwd.subscription.entity.Status;

import java.util.List;
import java.util.Optional;

public class StatusService implements EntityService<Status> {

    private final StatusDao statusDao;

    public StatusService(StatusDao statusDao) {
        this.statusDao = statusDao;
    }

    @Override
    public List<Status> findAll() {
        return null;
    }

    @Override
    public Optional<Status> findById(Long id) {
        return statusDao.read(id);
    }

    @Override
    public Optional<Status> create(Status entity) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return statusDao.delete(id);
    }
}
