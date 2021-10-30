package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.entity.Entity;

public interface ServiceFactory {

    <T extends Entity> EntityService<T> serviceFor(Class<T> entityClass);

    static SimpleServiceFactory instance() {
        return SimpleServiceFactory.getInstance();
    }
}
