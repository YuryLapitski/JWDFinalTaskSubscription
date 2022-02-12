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

    static SimpleServiceFactory getServiceInstance() {
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
                    return new SimpleAccountService(AccountDao.getInstance(), BCrypt.withDefaults(), BCrypt.verifyer());
                case "Edition":
                    return new SimpleEditionService(EditionDao.getInstance());
                case "User":
                    return new SimpleUserService(UserDao.getInstance());
                case "Price":
                    return new SimplePriceService(PriceDao.getInstance());
                case "Address":
                    return new SimpleAddressService(AddressDao.getInstance());
                case "Term":
                    return new TermService(TermDao.getInstance());
                case "Status":
                    return new StatusService(StatusDao.getInstance());
                case "Subscription":
                    return new SimpleSubscriptionService(SubscriptionDao.getInstance());
                case "Card":
                    return new SimpleCardService(CardDao.getInstance());
                case "Archive":
                    return new SimpleArchiveService(ArchiveDao.getInstance());
                default:
                    throw new IllegalArgumentException(String.format(SERVICE_NOT_FOUND, className));
            }
        };
    }
}
