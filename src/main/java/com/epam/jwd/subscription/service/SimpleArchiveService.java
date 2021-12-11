package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.AddressDao;
import com.epam.jwd.subscription.dao.ArchiveDao;
import com.epam.jwd.subscription.entity.Archive;

import java.util.List;
import java.util.Optional;

public class SimpleArchiveService implements ArchiveService {

    private final ArchiveDao archiveDao;

    public SimpleArchiveService(ArchiveDao archiveDao) {
        this.archiveDao = archiveDao;
    }

    @Override
    public List<Archive> findByAccId(Long accId) {
        return archiveDao.findByAccId(accId);
    }

    @Override
    public List<Archive> findAll() {
        return archiveDao.read();
    }

    @Override
    public Optional<Archive> findById(Long id) {
        return archiveDao.read(id);
    }

    @Override
    public Optional<Archive> create(Archive entity) {
        return Optional.ofNullable(archiveDao.create(entity));
    }

    @Override
    public boolean delete(Long id) {
        return archiveDao.delete(id);
    }
}
