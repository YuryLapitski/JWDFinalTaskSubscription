package com.epam.jwd.subscription.db;

import com.epam.jwd.subscription.exception.CouldNotInitializeConnectionPoolError;

import java.sql.Connection;

public interface ConnectionPool {

    boolean isInitialized();

    boolean init() throws CouldNotInitializeConnectionPoolError;

    boolean shutDown();

    Connection takeConnection() throws InterruptedException;

    void returnConnection (Connection connection);

    static ConnectionPool instance() {
        return LockingConnectionPool.getInstance();
    }
}
