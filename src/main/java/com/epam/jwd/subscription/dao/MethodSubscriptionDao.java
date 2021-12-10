package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.db.ConnectionPool;
import com.epam.jwd.subscription.entity.Subscription;
import com.epam.jwd.subscription.exception.EntityExtractionFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.String.format;

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
    private static final String UPDATE = "update %s set %s = ? where %s = ?";
    private static final String VALUES = "values (?, ?, ?, ?, ?, ?)";
    protected static final String WHERE_FIELDS = "where %s = ? and %s = ?  and %s = ? and %s = ? and %s = ? and %s = ?";
    protected static final String WHERE_ONE_FIELD = "where %s = ?";
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
    private final String updateSql;
    private final String findIdByAllExpression;
    private final String findByEditionIdSql;

    private MethodSubscriptionDao(ConnectionPool pool) {
        super(pool, LOG);
        this.updateSql = format(UPDATE, getTableName(), STATUS_ID_FIELD_NAME, ID_FIELD_NAME);
        this.findIdByAllExpression = format(SELECT_ALL_FROM, String.join(COMMA, getFields())) +
                getTableName() + SPACE + format(WHERE_FIELDS, USER_ID_FIELD_NAME, ADDRESS_ID_FIELD_NAME,
                EDITION_ID_FIELD_NAME, TERM_ID_FIELD_NAME, PRICE_ID_FIELD_NAME, STATUS_ID_FIELD_NAME);
        this.findByEditionIdSql = format(SELECT_ALL_FROM, String.join(COMMA, getFields())) +
                getTableName() + SPACE + format(WHERE_ONE_FIELD, EDITION_ID_FIELD_NAME);
    }

    public static MethodSubscriptionDao getInstance(){
        if(instance == null) {
            try {
                LOCK.lock();
                if(instance == null) {
                    instance = new MethodSubscriptionDao(ConnectionPool.lockingInstance());
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
    public List<Subscription> findIdByAll(Long userId, Long addressId, Long editionId, Long termId,
                                          Long priceId, Long statusId) {
        try {
            return executePreparedForEntities(findIdByAllExpression,
                    this::extractResultCatchingException,
                    st -> fillAllParameters(st, userId, addressId, editionId, termId, priceId, statusId));
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Subscription> findByEditionId(Long editionId) {
        try {
            return executePreparedForEntities(findByEditionIdSql,
                    this::extractResultCatchingException,
                    st -> st.setLong(1, editionId));
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }

    @Override
    public void updateStatus(Long statusId, Long subscriptionId) {
        try {
            final int rowsUpdated = executePreparedUpdate(updateSql,
                    st -> fillParameters(st, statusId, subscriptionId));
            if (rowsUpdated > 0) {
                LOG.info("Updated successfully. New status id for subscriptionId {} is {}",
                        subscriptionId, statusId);
            } else {
                LOG.error("Update error occurred");
            }
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    private void fillParameters(PreparedStatement statement, Long statusId, Long subscriptionId) throws SQLException {
        statement.setLong(1, statusId);
        statement.setLong(2, subscriptionId);
    }

    private void fillAllParameters(PreparedStatement statement, Long userId, Long addressId, Long editionId,
                                   Long termId, Long priceId, Long statusId) throws SQLException {
        statement.setLong(1, userId);
        statement.setLong(2, addressId);
        statement.setLong(3, editionId);
        statement.setLong(4, termId);
        statement.setLong(5, priceId);
        statement.setLong(6, statusId);
    }
}
