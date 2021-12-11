package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Archive;
import com.epam.jwd.subscription.entity.Subscription;

import java.util.List;

public interface ArchiveDao extends EntityDao<Archive> {

    List<Archive> findByAccId (Long accId);

    static ArchiveDao instance() {
        return MethodArchiveDao.getInstance();
    }

}
