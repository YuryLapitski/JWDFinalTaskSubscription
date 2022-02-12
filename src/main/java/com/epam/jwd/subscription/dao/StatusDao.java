package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.entity.Status;

public interface StatusDao extends EntityDao<Status> {

    static StatusDao getInstance() {
        return MethodStatusDao.getDaoInstance();
    }
}
