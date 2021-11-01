package com.epam.jwd.subscription.service;

import com.epam.jwd.subscription.dao.AccountDao;
import com.epam.jwd.subscription.dao.EditionDao;
import com.epam.jwd.subscription.dao.UserDao;
import com.epam.jwd.subscription.entity.Entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class SimpleServiceFactory implements ServiceFactory {

    private static final String SERVICE_NOT_FOUND = "Could not create service for %s class";

    private final Map<Class<?>, EntityService<?>> serviceByEntity = new ConcurrentHashMap<>();

    private SimpleServiceFactory() {
    }

    private static class Holder {
        public static final SimpleServiceFactory INSTANCE = new SimpleServiceFactory();
    }

    static SimpleServiceFactory getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Entity> EntityService<T> serviceFor(Class<T> entityClass) {
        return (EntityService<T>) serviceByEntity.computeIfAbsent(entityClass, createServiceFromClass());
    }

    private Function<Class<?>, EntityService<?>> createServiceFromClass() {
        return clazz -> {
            final String className = clazz.getSimpleName();
            switch (className) {
                case "Account":
                    return new AccountService(AccountDao.instance());
                case "Edition":
                    return new EditionService(EditionDao.instance());
                case "User":
                    return new UserService(UserDao.instance());
                default:
                    throw new IllegalArgumentException(String.format(SERVICE_NOT_FOUND, className));
            }
        };
    }
}
