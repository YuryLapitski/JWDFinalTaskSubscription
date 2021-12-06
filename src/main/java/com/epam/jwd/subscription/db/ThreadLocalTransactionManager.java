package com.epam.jwd.subscription.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class  ThreadLocalTransactionManager implements TransactionManager {

    private static final Logger LOGGER = LogManager.getLogger(ThreadLocalTransactionManager.class);
    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final ThreadLocal<TransactionId> THREAD_CONNECTION = ThreadLocal.withInitial(() -> {
        try {
            return new SimpleTransactionId(new ProxyConnection(ConnectionPool.lockingInstance().takeConnection(), ConnectionPool.lockingInstance()));
        } catch (InterruptedException e) {
            LOGGER.warn("Thread was interrupted", e);
            Thread.currentThread().interrupt();
            return null;
        }
    });

    private static ThreadLocalTransactionManager instance = null;

    private ThreadLocalTransactionManager() {
    }

    public static ThreadLocalTransactionManager getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new ThreadLocalTransactionManager();
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    @Override
    public void initTransaction() {
        final Connection connection = THREAD_CONNECTION.get().getConnection();
        try {
            LOCK.lock();
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            } //todo: otherwise throw exception
        } catch (SQLException e) {
            LOGGER.error("SQL exc occurred trying to initialize transaction", e);
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public void commitTransaction() {
        final Connection connection = THREAD_CONNECTION.get().getConnection();
        try {
            LOCK.lock();
            if (!connection.getAutoCommit()) {
                connection.commit();
                connection.setAutoCommit(true);
            }  //todo: otherwise throw exception
            THREAD_CONNECTION.remove();
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("SQL exc occurred committing transaction", e);
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public boolean isTransactionActive() {
        try {
            LOCK.lock();
            final Connection connection = THREAD_CONNECTION.get().getConnection();
            final boolean transactionActive = !connection.getAutoCommit();
            if (!transactionActive) {
                THREAD_CONNECTION.remove();
                connection.close(); //should return connection to Connection Pool
            }
            return transactionActive;
        } catch (SQLException e) {
            LOGGER.error("SQL exc occurred trying to check transaction status", e);
            return false;
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public Optional<TransactionId> getTransactionId() {
        try {
            LOCK.lock();
            final TransactionId transactionId = THREAD_CONNECTION.get();
            if (transactionId.getConnection().getAutoCommit()) {
                THREAD_CONNECTION.remove();
                ((ProxyConnection) transactionId.getConnection()).getConnection().close();
                return Optional.empty();
            }
            return Optional.of(transactionId);
        } catch (SQLException e) {
            LOGGER.error("SQL exc occurred trying to retrieve transaction id", e);
            return Optional.empty();
        } finally {
            LOCK.unlock();
        }
    }
}
