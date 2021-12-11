package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Archive;

import java.util.List;

public interface ArchiveService extends EntityService<Archive> {

    List<Archive> findByAccId (Long accId);
}
