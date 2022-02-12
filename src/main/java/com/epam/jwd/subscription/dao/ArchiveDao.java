package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Archive;

import java.util.List;

public interface ArchiveDao extends EntityDao<Archive> {

    List<Archive> findByAccId (Long accId);

    static ArchiveDao getInstance() {
        return MethodArchiveDao.getDaoInstance();
    }

}
