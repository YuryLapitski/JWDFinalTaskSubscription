package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.db.ConnectionPool;
import com.epam.jwd.subscription.entity.Card;
import com.epam.jwd.subscription.exception.EntityExtractionFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.String.format;

public class MethodCardDao extends CommonDao<Card> implements CardDao  {

    private static final Logger LOG = LogManager.getLogger(MethodCardDao.class);

    private static MethodCardDao instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final String CARD_TABLE_NAME = "card";
    private static final String ID_FIELD_NAME = "id";
    private static final String CARD_NAME_FIELD_NAME = "card_name";
    private static final String CARD_NUMBER_FIELD_NAME = "card_number";
    private static final String AMOUNT_FIELD_NAME = "amount";
    private static final String UPDATE = "update %s set %s = ? where %s = ?";
    private static final String COMMA = ", ";


    private static final List<String> FIELDS = Arrays.asList(
            ID_FIELD_NAME, CARD_NAME_FIELD_NAME,
            CARD_NUMBER_FIELD_NAME, AMOUNT_FIELD_NAME
    );

    private static final List<String> FIELDS_FOR_INSERT = Arrays.asList(
            CARD_NAME_FIELD_NAME, CARD_NUMBER_FIELD_NAME, AMOUNT_FIELD_NAME
    );

    private final String selectByNumberExpression;
    protected final String updateSql;

    private MethodCardDao(ConnectionPool pool) {
        super(pool, LOG);
        this.selectByNumberExpression = format(SELECT_ALL_FROM, String.join(COMMA, getFields())) +
                getTableName() + SPACE + format(WHERE_FIELD, CARD_NUMBER_FIELD_NAME);
        this.updateSql = format(UPDATE, getTableName(), AMOUNT_FIELD_NAME, CARD_NUMBER_FIELD_NAME);

    }

    public static MethodCardDao getInstance(){
        if(instance == null) {
            try {
                LOCK.lock();
                if(instance == null) {
                    instance = new MethodCardDao(ConnectionPool.instance());
                }
            } finally {
                LOCK.unlock();
            }
        } return instance;
    }

    @Override
    protected String getTableName() {
        return CARD_TABLE_NAME;
    }

    @Override
    protected String getInsertTableName() {
        return CARD_TABLE_NAME;
    }

    @Override
    protected List<String> getFields() {
        return FIELDS;
    }

    @Override
    protected List<String> getInsertFields() {
        return FIELDS_FOR_INSERT;
    }

    @Override
    protected String getValues() {
        return null;
    }

    @Override
    protected String getIdFieldName() {
        return ID_FIELD_NAME;
    }

    @Override
    protected Card extractResult(ResultSet rs) throws SQLException, EntityExtractionFailedException {
        try {
            return new Card(
                    rs.getLong(ID_FIELD_NAME),
                    rs.getString(CARD_NAME_FIELD_NAME),
                    rs.getString(CARD_NUMBER_FIELD_NAME),
                    rs.getBigDecimal(AMOUNT_FIELD_NAME));
        } catch (SQLException e) {
            LOG.error("sql exception occurred extracting edition from ResultSet", e);
            throw new EntityExtractionFailedException("could not extract entity", e);
        }
    }

    @Override
    protected void fillEntity(PreparedStatement statement, Card entity) throws SQLException {

    }

    @Override
    public Card update(Card entity) {
        try {
            final int rowsUpdated = executePreparedUpdate(updateSql,
                    st -> fillParameters(st, entity.getAmount(), entity.getCardNumber()));
            if (rowsUpdated > 0) {
//                read() //todo: read by unique param
                return null;
            }
            return null; //todo: throw exc
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return null;
        }
    }

    @Override
    public Optional<Card> readCardByNumber(String cardNumber) {
        try {
            return executePreparedForGenericEntity(selectByNumberExpression,
                    this::extractResultCatchingException,
                    st -> st.setString(1, cardNumber));
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    @Override
    public void updateAmount (BigDecimal amount, String cardNumber) {
        try {
            final int rowsUpdated = executePreparedUpdate(updateSql,
                    st -> fillParameters(st, amount, cardNumber));
            if (rowsUpdated > 0) {
                LOG.info("Updated successfully. New amount for card {} is {}", cardNumber, amount);
            } else {
                LOG.error("Update error occurred");
            }
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    private void fillParameters(PreparedStatement statement, BigDecimal amount, String cardNumber) throws SQLException {
        statement.setBigDecimal(1, amount);
        statement.setString(2, cardNumber);
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
