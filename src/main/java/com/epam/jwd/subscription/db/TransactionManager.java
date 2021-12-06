package com.epam.jwd.subscription.db;

import java.util.Optional;

public interface TransactionManager {

    void initTransaction();

    void commitTransaction();

    boolean isTransactionActive();

    Optional<TransactionId> getTransactionId();

    static TransactionManager instance() {
        return ThreadLocalTransactionManager.getInstance();
    }

}
