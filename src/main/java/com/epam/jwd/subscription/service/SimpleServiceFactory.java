package com.epam.jwd.subscription.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.epam.jwd.subscription.dao.*;
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
                    return new SimpleAccountService(AccountDao.instance(), BCrypt.withDefaults(), BCrypt.verifyer());
                case "Edition":
                    return new SimpleEditionService(EditionDao.instance());
                case "User":
                    return new SimpleUserService(UserDao.instance());
                case "Price":
                    return new SimplePriceService(PriceDao.instance());
                case "Address":
                    return new SimpleAddressService(AddressDao.instance());
                case "Term":
                    return new TermService(TermDao.instance());
                case "Status":
                    return new StatusService(StatusDao.instance());
                case "Subscription":
                    return new SimpleSubscriptionService(SubscriptionDao.instance());
                case "Card":
                    return new SimpleCardService(CardDao.instance());
                case "Archive":
                    return new SimpleArchiveService(ArchiveDao.instance());
                default:
                    throw new IllegalArgumentException(String.format(SERVICE_NOT_FOUND, className));
            }
        };
    }
}
