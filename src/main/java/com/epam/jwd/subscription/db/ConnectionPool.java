package com.epam.jwd.subscription.db;

import com.epam.jwd.subscription.exception.CouldNotInitializeConnectionPool;

import java.sql.Connection;

public interface ConnectionPool {

    boolean isInitialized() throws CouldNotInitializeConnectionPool;

    boolean init();

    boolean shutDown();

    Connection takeConnection() throws InterruptedException;

    void returnConnection (Connection connection);

    static ConnectionPool instance() {
        return LockingConnectionPool.getInstance();
    }
}
