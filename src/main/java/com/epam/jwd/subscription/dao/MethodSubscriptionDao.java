package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.db.ConnectionPool;
import com.epam.jwd.subscription.entity.Subscription;
import com.epam.jwd.subscription.entity.User;
import com.epam.jwd.subscription.exception.EntityExtractionFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.String.format;
import static java.lang.String.join;

public class MethodSubscriptionDao extends CommonDao<Subscription> implements SubscriptionDao {

    private static final Logger LOG = LogManager.getLogger(MethodSubscriptionDao.class);

    private static MethodSubscriptionDao instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final String SUBSCRIPTION_TABLE_NAME = "subscription";
    private static final String ID_FIELD_NAME = "id";
    private static final String USER_ID_FIELD_NAME = "user_id";
    private static final String ADDRESS_ID_FIELD_NAME = "address_id";
    private static final String EDITION_ID_FIELD_NAME = "edition_id";
    private static final String TERM_ID_FIELD_NAME = "term_id";
    private static final String PRICE_ID_FIELD_NAME = "price_id";
    private static final String STATUS_ID_FIELD_NAME = "status_id";
//    private static final String INSERT_INTO = "insert %s (%s)";
    private static final String VALUES = "values (?, ?, ?, ?, ?, ?)";
    protected static final String WHERE_FIELDS = "where %s = ? and %s = ?";
    private static final String COMMA = ", ";

    private static final List<String> FIELDS = Arrays.asList(
            ID_FIELD_NAME, USER_ID_FIELD_NAME, ADDRESS_ID_FIELD_NAME, EDITION_ID_FIELD_NAME,
            TERM_ID_FIELD_NAME, PRICE_ID_FIELD_NAME, STATUS_ID_FIELD_NAME
    );

    private static final List<String> FIELDS_FOR_INSERT = Arrays.asList(
            USER_ID_FIELD_NAME, ADDRESS_ID_FIELD_NAME, EDITION_ID_FIELD_NAME,
            TERM_ID_FIELD_NAME, PRICE_ID_FIELD_NAME, STATUS_ID_FIELD_NAME
    );

//    private final String insertSql;

    private MethodSubscriptionDao(ConnectionPool pool) {
        super(pool, LOG);
//        this.insertSql = format(INSERT_INTO, getTableName(), join(COMMA, getInsertFields()));
    }

    public static MethodSubscriptionDao getInstance(){
        if(instance == null) {
            try {
                LOCK.lock();
                if(instance == null) {
                    instance = new MethodSubscriptionDao(ConnectionPool.instance());
                }
            } finally {
                LOCK.unlock();
            }
        } return instance;
    }

    @Override
    protected String getTableName() {
        return SUBSCRIPTION_TABLE_NAME;
    }

    @Override
    protected String getInsertTableName() {
        return SUBSCRIPTION_TABLE_NAME;
    }

    @Override
    protected List<String> getFields() {
        return FIELDS;
    }

    protected List<String> getInsertFields() {
        return FIELDS_FOR_INSERT;
    }

    @Override
    protected String getValues() {
        return VALUES;
    }

    @Override
    protected String getIdFieldName() {
        return ID_FIELD_NAME;
    }

    @Override
    protected Subscription extractResult(ResultSet rs) throws SQLException, EntityExtractionFailedException {
        try {
            return new Subscription(
                    rs.getLong(ID_FIELD_NAME),
                    rs.getLong(USER_ID_FIELD_NAME),
                    rs.getLong(ADDRESS_ID_FIELD_NAME),
                    rs.getLong(EDITION_ID_FIELD_NAME),
                    rs.getLong(TERM_ID_FIELD_NAME),
                    rs.getLong(PRICE_ID_FIELD_NAME),
                    rs.getLong(STATUS_ID_FIELD_NAME));
        } catch (SQLException e) {
            LOG.error("sql exception occurred extracting user from ResultSet", e);
            throw new EntityExtractionFailedException("could not extract entity", e);
        }
    }

//    @Override
//    public Subscription create(Subscription entity) {
//        try {
//            final int rowsUpdated = executePreparedUpdate(insertSql, st -> fillEntity(st, entity));
//            if (rowsUpdated > 0) {
////                read() //todo: read by unique param
//                return null;
//            }
//            return null; //todo: throw exc
//        } catch (InterruptedException e) {
//            LOG.info("takeConnection interrupted", e);
//            Thread.currentThread().interrupt();
//            return null;
//        }
//    }

    @Override
    protected void fillEntity(PreparedStatement statement, Subscription subscription) throws SQLException {
        statement.setLong(1, subscription.getUserId());
        statement.setLong(2, subscription.getAddressId());
        statement.setLong(3, subscription.getEditionId());
        statement.setLong(4, subscription.getTermId());
        statement.setLong(5, subscription.getPriceId());
        statement.setLong(6, subscription.getStatusId());
    }

    @Override
    public Subscription update(Subscription entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
