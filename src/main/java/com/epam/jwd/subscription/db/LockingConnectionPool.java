package com.epam.jwd.subscription.db;

import com.epam.jwd.subscription.exception.CouldNotInitializeConnectionPoolError;
import com.epam.jwd.subscription.exception.ResourcesLoadingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockingConnectionPool implements ConnectionPool {

    private static final Logger LOG = LogManager.getLogger(LockingConnectionPool.class);
    private static final ReentrantLock LOCK = new ReentrantLock();
    private static final Condition CONDITION = LOCK.newCondition();
    private static final double CONNECTION_INCREASE_FACTOR = 0.25;
    private static final String RESOURCES_PATH = "connectionPool.properties";
    private final Queue<ProxyConnection> availableConnections = new ConcurrentLinkedQueue<>();
    private final List<ProxyConnection> givenAwayConnections = new ArrayList<>();
    private boolean initialized = false;
    private static LockingConnectionPool instance = null;

    private LockingConnectionPool() {
    }

    public static LockingConnectionPool getInstance() {
        if (instance == null) {
            try {
                LOCK.lock();
                if (instance == null) {
                    instance = new LockingConnectionPool();
                }
            } finally {
                LOCK.unlock();
            }
        }
        return instance;
    }

    @Override
    public boolean isInitialized() {
        LOCK.lock();
        try {
            return initialized;
        } finally {
            LOCK.unlock();
        }

    }

    @Override
    public boolean init() {
        try {
            LOCK.lock();
            if (!initialized) {
                initializeConnections(initialPoolSizeFromProperties(readProperties()),true);
                initialized = true;
                return true;
            }
            return false;
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public boolean shutDown() {
        try {
            LOCK.lock();
            if (initialized) {
                closeConnections();
                deregisterDrivers();
                initialized = false;
                return true;
            }
            return false;
        } finally {
            LOCK.unlock();
        }
    }

    @Override
    public Connection takeConnection() throws InterruptedException {
        try {
            LOCK.lock();
            while (availableConnections.isEmpty()) {
                CONDITION.await();
            }
            final ProxyConnection connection = availableConnections.poll();
            LOG.trace("{} take connection...", Thread.currentThread().getName());
            LOG.trace("available connection size {}", availableConnections.size());
            givenAwayConnections.add(connection);
            LOG.trace("given away connection size {}", givenAwayConnections.size());
            int currentSize = availableConnections.size() + givenAwayConnections.size();
            LOG.trace("current size {}", currentSize);
            if (availableConnections.size() < CONNECTION_INCREASE_FACTOR * currentSize) {
                LOG.warn("need to initialize new connections");
                initializeConnections(initialPoolSizeFromProperties(readProperties()), true);
            }
            return connection;
        } finally {
            LOCK.unlock();
        }
    }

    private void closeConnections() {
        closeConnections(this.availableConnections);
        closeConnections(this.givenAwayConnections);
    }

    private void closeConnections(Collection<ProxyConnection> connections) {
        for (ProxyConnection conn : connections) {
            closeConnection(conn);
        }
    }

    private void closeConnection(ProxyConnection conn) {
        try {
            conn.realClose();
            LOG.info("closed connection {}", conn);
        } catch (SQLException e) {
            LOG.error("Could not close connection", e);
        }
    }

    @Override
    @SuppressWarnings("SuspiciousMethodCalls")
    public void returnConnection(Connection connection) {
        try {
            LOCK.lock();
            if (givenAwayConnections.remove(connection)) {
                availableConnections.add((ProxyConnection) connection);
                LOG.trace("{} return connection...", Thread.currentThread().getName());
                LOG.trace("available connection size {}", availableConnections.size());
                LOG.trace("given away connection size {}", givenAwayConnections.size());
                int currentSize = availableConnections.size() + givenAwayConnections.size();
                LOG.trace("current size {}", currentSize);
                if (availableConnections.size() > initialPoolSizeFromProperties(readProperties())) {
                    LOG.warn("need to decrease connections");
                    availableConnections.remove(connection);
                    int currentSize1 = availableConnections.size() + givenAwayConnections.size();
                    LOG.trace("current size {}", currentSize1);
                }
                CONDITION.signalAll();
            } else {
                LOG.warn("Attempt to add unknown connection to Connection Pool. Connection: {}", connection);
            }
        } finally {
            LOCK.unlock();
        }
    }

    private Properties readProperties() {
        Properties properties = new Properties();
        try {
            ClassLoader loader = getClass().getClassLoader();
            InputStream inputStream = loader.getResourceAsStream(RESOURCES_PATH);
            properties.load(inputStream);
        } catch (IOException e) {
            LOG.error("Failed to load resources from properties file (" + RESOURCES_PATH + ").");
            throw new ResourcesLoadingException("An error occurred while loading resources", e);
        }
        return properties;
    }

    private String urlFromProperties (Properties properties) {
        return properties.getProperty("url");
    }

    private String userFromProperties (Properties properties) {
        return properties.getProperty("user");
    }

    private String passwordFromProperties (Properties properties) {
        return properties.getProperty("password");
    }

    private int initialPoolSizeFromProperties (Properties properties) {
        return Integer.parseInt(properties.getProperty("initialPoolSize"));
    }

    private void initializeConnections(int amountConnection, boolean failOnConnectionException) {
        try {
            for (int i = 0; i < amountConnection; i++) {
                final Connection connection = DriverManager.getConnection(urlFromProperties(readProperties()),
                        userFromProperties(readProperties()),
                        passwordFromProperties(readProperties()));
                LOG.info("Initialized connection {}", connection);
                final ProxyConnection proxyConnection = new ProxyConnection(connection, this);
                availableConnections.add(proxyConnection);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred creating connection", e);
            if (failOnConnectionException) {
                throw new CouldNotInitializeConnectionPoolError("Failed to create connection", e);
            }
        }
    }

    private static void deregisterDrivers() {
        LOG.trace("unregistering sql drivers");
        final Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            try {
                DriverManager.deregisterDriver(drivers.nextElement());
            } catch (SQLException e) {
                LOG.error("could not deregister driver", e);
            }
        }
    }
}
