package com.epam.jwd.subscription.app;

import com.epam.jwd.subscription.db.*;
import com.epam.jwd.subscription.entity.Entity;
import com.epam.jwd.subscription.entity.User;
import com.epam.jwd.subscription.exception.EntityExtractionFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.Collections;
import java.util.List;

public class Application {

    private static final Logger LOG = LogManager.getLogger(Application.class);
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dbsubscription";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static final String ID_COLUMN_NAME = "id";
    private static final String FIRST_NAME_COLUMN_NAME = "first_name";
    private static final String LAST_NAME_COLUMN_NAME = "last_name";
    private static final String AGE_COLUMN_NAME = "age";
    private static final String EMAIL_COLUMN_NAME = "email";
    private static final String SELECT_ALL_SQL = "Select id as id, first_name as first_name," +
            " last_name as last_name, age as age, email as email from user";

    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.instance();

    public static void main(String[] args) {
        Thread thread1 = new JThread("Thread1");
        Thread thread2 = new JThread("Thread2");
        Thread thread3 = new JThread("Thread3");
        Thread thread4 = new JThread("Thread4");
        Thread thread5 = new JThread("Thread5");
        Thread thread6 = new JThread("Thread6");
        Thread thread7 = new JThread("Thread7");
        Thread thread8 = new JThread("Thread8");
        Thread thread9 = new JThread("Thread9");
        Thread thread10 = new JThread("Thread10");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
        thread6.start();
        thread7.start();
        thread8.start();
        thread9.start();
        thread10.start();

//        CONNECTION_POOL.init();
//        final List<User> users;
//        try {
//            users = fetchUsersFromDB();
//        } catch (InterruptedException e) {
//            LOG.info("interrupted fetching users. closing pool");
//            CONNECTION_POOL.shutDown();
//            return;
//        }
//        users.forEach(user -> LOG.info("Found {}", user));
//        CONNECTION_POOL.shutDown();
    }

    private static <T extends Entity> List<T> executeStatement(String sql,
                                                               ResultSetExtractor<T>
                                                                         extractor) throws InterruptedException {
        try (final Connection connection = CONNECTION_POOL.takeConnection();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(sql)) {

            return extractor.extractAll(resultSet);
        } catch (SQLException e) {
            LOG.error("SQL exception occurred", e);
            LOG.debug("SQL: {}", sql);
        } catch (EntityExtractionFailedException e) {
            LOG.error("Could not extract entity", e);
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            throw e;
        }
        return Collections.emptyList();
    }

    private static <T extends Entity> List<T> executePrepareded (String sql,
                                                                 ResultSetExtractor<T> extractor,
                                                                 StatementPreparator
                                                                           statementPreparation) throws InterruptedException {
        try (final Connection connection = CONNECTION_POOL.takeConnection();
             final PreparedStatement statement = connection.prepareStatement(sql)) {
            if (statementPreparation != null) {
                statementPreparation.accept(statement);
            }
            final ResultSet resultSet = statement.executeQuery();
            return extractor.extractAll(resultSet);
        } catch (SQLException e) {
            LOG.error("SQL exception occured", e);
            LOG.debug("SQL: {}", sql);
        } catch (EntityExtractionFailedException e) {
            LOG.error("Could not extract entity", e);
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            throw e;
        }
        return Collections.emptyList();
    }

    private static User extractUser (ResultSet resultSet) throws EntityExtractionFailedException {
        try {
            return new User(resultSet.getLong(ID_COLUMN_NAME),
                    resultSet.getString(FIRST_NAME_COLUMN_NAME),
                    resultSet.getString(LAST_NAME_COLUMN_NAME),
                    resultSet.getInt(AGE_COLUMN_NAME),
                    resultSet.getString(EMAIL_COLUMN_NAME));
        } catch (SQLException e) {
            LOG.error("could not extract value from result set", e);
            throw new EntityExtractionFailedException("failed to extract user");
        }
    }
    static List<User> fetchUsersFromDB () throws InterruptedException {
        return executeStatement(SELECT_ALL_SQL, Application::extractUser);
    }

}

class JThread extends Thread {

    private static final Logger LOG = LogManager.getLogger(JThread.class);
    private static final ConnectionPool CONNECTION_POOL = ConnectionPool.instance();

    JThread(String name){
        super(name);
    }

    public void run(){
        System.out.printf("%s started... \n", Thread.currentThread().getName());
        CONNECTION_POOL.init();
        final List<User> users;
        try {
            users = Application.fetchUsersFromDB();

        } catch (InterruptedException e) {
            LOG.info("interrupted fetching users. closing pool");
            CONNECTION_POOL.shutDown();
            return;
        }
        users.forEach(user -> LOG.info("{} Found {}", Thread.currentThread().getName(), user));
//        try {
//            wait(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        CONNECTION_POOL.shutDown();
        System.out.printf("%s fiished... \n", Thread.currentThread().getName());
    }
}
