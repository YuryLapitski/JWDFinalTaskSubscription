package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.db.ConnectionPool;
import com.epam.jwd.subscription.entity.Category;
import com.epam.jwd.subscription.entity.Edition;
import com.epam.jwd.subscription.entity.Price;
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

import static java.lang.String.format;
import static java.lang.String.join;

public class MethodEditionDao extends CommonDao<Edition> implements EditionDao {

    private static final Logger LOG = LogManager.getLogger(MethodEditionDao.class);

    private static final String EDITION_CAT_TABLE_NAME = "edition e join category c on c.cat_id = e.cat_id";
    private static final String EDITION_TABLE_NAME = "edition";
    private static final String EDITION_ID_FIELD_NAME = "ed_id";
    private static final String NAME_FIELD_NAME = "ed_name";
    private static final String CATEGORY_ID_FIELD_NAME = "cat_id";
    private static final String CATEGORY_FIELD_NAME = "cat_name";
    private static final Long THREE_MONTHS_TERM_ID = 1L;
    private static final Long SIX_MONTHS_TERM_ID = 2L;
    private static final Long TWELVE_MONTHS_TERM_ID = 3L;
    private static final String UPDATE = "update %s set %s = ?, %s = ? where %s = ?";
    private static final String INSERT_INTO = "insert into %s (%s)";
    private static final String COMMA = ", ";
    private static final String VALUES = "values (?, ?)";

    private static final List<String> FIELDS = Arrays.asList(
            EDITION_ID_FIELD_NAME, NAME_FIELD_NAME,
            CATEGORY_FIELD_NAME
    );

    private static final List<String> FIELDS_FOR_INSERT = Arrays.asList(
            NAME_FIELD_NAME, CATEGORY_ID_FIELD_NAME
    );

    private final String updateByEditionIdSql;
    private final String insertSql;
    private final String deleteSql;
    private final String selectByNameExpression;

    private MethodEditionDao(ConnectionPool pool) {
        super(pool, LOG);
        this.updateByEditionIdSql = format(UPDATE, EDITION_TABLE_NAME, NAME_FIELD_NAME, CATEGORY_ID_FIELD_NAME,
                EDITION_ID_FIELD_NAME);
        this.insertSql = format(INSERT_INTO, getInsertTableName(),
                join(COMMA, getInsertFields())) + SPACE + getValues();
        this.selectByNameExpression = selectAllExpression + SPACE + format(WHERE_FIELD, NAME_FIELD_NAME);
        this.deleteSql = format(DELETE_FROM, EDITION_TABLE_NAME, getIdFieldName());
    }

    private static class Holder {
        public static final EditionDao INSTANCE = new MethodEditionDao(ConnectionPool.lockingInstance());
    }


    static EditionDao getDaoInstance() {
        return Holder.INSTANCE;
    }

    @Override
    protected String getTableName() {
        return EDITION_CAT_TABLE_NAME;
    }

    @Override
    protected String getInsertTableName() {
        return EDITION_TABLE_NAME;
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
        return VALUES;
    }

    @Override
    protected String getIdFieldName() {
        return EDITION_ID_FIELD_NAME;
    }

    private BigDecimal priceValue(Long editionId, Long termId) {
        final List<Price> prices = PriceDao.getInstance().findPricesByEditionId(editionId);
        for (Price price : prices) {
            if (price.getTermId().equals(termId)) {
                return price.getValue();
            }
        }
        return null;
    }

    @Override
    protected Edition extractResult(ResultSet rs) throws EntityExtractionFailedException {
        try {
            return new Edition(
                    rs.getLong(EDITION_ID_FIELD_NAME),
                    rs.getString(NAME_FIELD_NAME),
                    Category.of(rs.getString(CATEGORY_FIELD_NAME)),
                    priceValue(rs.getLong(EDITION_ID_FIELD_NAME), THREE_MONTHS_TERM_ID),
                    priceValue(rs.getLong(EDITION_ID_FIELD_NAME), SIX_MONTHS_TERM_ID),
                    priceValue(rs.getLong(EDITION_ID_FIELD_NAME), TWELVE_MONTHS_TERM_ID));
        } catch (SQLException e) {
            LOG.error("sql exception occurred extracting edition from ResultSet", e);
            throw new EntityExtractionFailedException("could not extract entity", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            final int rowsUpdated = executePreparedUpdate(deleteSql, st -> st.setLong(1, id));
            if (rowsUpdated > 0) {
                return true;
            }
            return false;
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    protected void fillEntity(PreparedStatement statement, Edition entity) throws SQLException {

    }

    @Override
    public Optional<Edition> findByName(String name) {
        try {
            return executePreparedForGenericEntity(selectByNameExpression,
                    this::extractResultCatchingException,
                    st -> st.setString(1, name));
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    @Override
    public Optional<Edition> findEditionById(Long id) {
        try {
            return executePreparedForGenericEntity(selectByIdExpression,
                    this::extractResultCatchingException,
                    st -> st.setLong(1, id));
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    @Override
    public void updateByEditionId(String name, Long catId, Long editionId) {
        try {
            final int rowsUpdated = executePreparedUpdate(updateByEditionIdSql,
                    st -> fillParameters(st, name, catId, editionId));
            if (rowsUpdated > 0) {
                LOG.info("Updated edition for id {} successfully.", editionId);
            } else {
                LOG.error("Update error occurred");
            }
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    private void fillParameters(PreparedStatement statement, String name, Long catId, Long editionId) throws SQLException {
        statement.setString(1, name);
        statement.setLong(2, catId);
        statement.setLong(3, editionId);
    }

    @Override
    public void addEdition(String name, Long catId) {
        try {
            final int rowsUpdated = executePreparedUpdate(insertSql,
                    st -> fillAddParameters(st, name, catId));
            if (rowsUpdated > 0) {
                LOG.info("Add new edition {} successfully.", name);
            } else {
                LOG.error("Update error occurred");
            }
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    private void fillAddParameters(PreparedStatement statement, String name, Long catId) throws SQLException {
        statement.setString(1, name);
        statement.setLong(2, catId);
    }

    @Override
    public Edition update(Edition entity) {
        return null;
    }
}
