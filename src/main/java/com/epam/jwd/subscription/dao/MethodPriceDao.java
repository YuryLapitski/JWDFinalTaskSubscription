package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.db.ConnectionPool;
import com.epam.jwd.subscription.entity.Address;
import com.epam.jwd.subscription.entity.Price;
import com.epam.jwd.subscription.exception.EntityExtractionFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.String.format;

public class MethodPriceDao extends CommonDao<Price> implements PriceDao {

    private static final Logger LOG = LogManager.getLogger(MethodPriceDao.class);

    private static MethodPriceDao instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final String PRICE_TABLE_NAME = "price";
    private static final String ID_FIELD_NAME = "pr_id";
    private static final String EDITION_ID_FIELD_NAME = "edition_id";
    private static final String TERM_ID_FIELD_NAME = "term_id";
    private static final String VALUE_FIELD_NAME = "pr_value";
    protected static final String WHERE_FIELDS = "where %s = ? and %s = ?";
    private static final String COMMA = ", ";

    private static final List<String> FIELDS = Arrays.asList(
            ID_FIELD_NAME, EDITION_ID_FIELD_NAME,
            TERM_ID_FIELD_NAME, VALUE_FIELD_NAME
    );

    private static final List<String> FIELDS_FOR_INSERT = Arrays.asList(
            EDITION_ID_FIELD_NAME, TERM_ID_FIELD_NAME, VALUE_FIELD_NAME
    );

    protected final String selectByEditionIdExpression;
    private final String selectByEditionIdTermIdExpression;

    private MethodPriceDao(ConnectionPool pool) {
        super(pool, LOG);
        this.selectByEditionIdExpression = selectAllExpression + SPACE
                + format(WHERE_FIELD, getEditionIdFieldName());
        this.selectByEditionIdTermIdExpression = format(SELECT_ALL_FROM, String.join(COMMA, getFields())) +
                getTableName() + SPACE + format(WHERE_FIELDS, EDITION_ID_FIELD_NAME, TERM_ID_FIELD_NAME);
    }

    public static MethodPriceDao getInstance(){
        if(instance == null) {
            try {
                LOCK.lock();
                if(instance == null) {
                    instance = new MethodPriceDao(ConnectionPool.instance());
                }
            } finally {
                LOCK.unlock();
            }
        } return instance;
    }

    @Override
    protected String getTableName() {
        return PRICE_TABLE_NAME;
    }

    @Override
    protected String getInsertTableName() {
        return null;
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
    protected Price extractResult(ResultSet rs) throws SQLException, EntityExtractionFailedException {
        try {
            return new Price(
                    rs.getLong(ID_FIELD_NAME),
                    rs.getLong(EDITION_ID_FIELD_NAME),
                    rs.getLong(TERM_ID_FIELD_NAME),
                    rs.getBigDecimal(VALUE_FIELD_NAME));
        } catch (SQLException e) {
            LOG.error("sql exception occurred extracting price from ResultSet", e);
            throw new EntityExtractionFailedException("could not extract entity", e);
        }
    }

    @Override
    protected void fillEntity(PreparedStatement statement, Price price) throws SQLException {
        statement.setLong(1, price.getEditionId());
        statement.setLong(2, price.getTermId());
        statement.setBigDecimal(3, price.getValue());
    }

    @Override
    public Price update(Price entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    protected String getEditionIdFieldName() {
        return EDITION_ID_FIELD_NAME;
    }

    @Override
    public List<Price> findPricesByEditionId(Long editionId) {
        try {
            return executePreparedForEntities(selectByEditionIdExpression,
                    this::extractResultCatchingException,
                    st -> st.setLong(1, editionId));
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Price> findByEditionIdTermID(Long editionId, Long termId) {
        try {
            Price price = new Price(editionId, termId);
            return executePreparedForGenericEntity(selectByEditionIdTermIdExpression,
                    this::extractResultCatchingException,
                    st -> fillEditionIdTermID(st, price));
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    private void fillEditionIdTermID(PreparedStatement statement, Price price) throws SQLException {
        statement.setLong(1, price.getEditionId());
        statement.setLong(2, price.getTermId());
    }
}
