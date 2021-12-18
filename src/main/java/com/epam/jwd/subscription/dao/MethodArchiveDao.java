package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.db.ConnectionPool;
import com.epam.jwd.subscription.entity.Archive;
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

public class MethodArchiveDao extends CommonDao<Archive> implements ArchiveDao {

    private static final Logger LOG = LogManager.getLogger(MethodArchiveDao.class);

    private static MethodArchiveDao instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final String ARCHIVE_TABLE_NAME = "archive";
    private static final String ID_FIELD_NAME = "id";
    private static final String ACC_ID_FIELD_NAME = "acc_id";
    private static final String EDITION_FIELD_NAME = "ed_name";
    private static final String TERM_FIELD_NAME = "term";
    private static final String PRICE_FIELD_NAME = "price";
    private static final String CITY_FIELD_NAME = "city";
    private static final String STREET_FIELD_NAME = "street";
    private static final String HOUSE_FIELD_NAME = "house";
    private static final String FLAT_FIELD_NAME = "flat";
    private static final String STATUS_FIELD_NAME = "status_name";
    private static final String DATE_FIELD_NAME = "date";
    private static final String COMMA = ", ";
    private static final String VALUES = "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    protected static final String WHERE_ONE_FIELD = "where %s = ?";

    private static final List<String> FIELDS = Arrays.asList(
            ID_FIELD_NAME, ACC_ID_FIELD_NAME, EDITION_FIELD_NAME, TERM_FIELD_NAME,
            PRICE_FIELD_NAME, CITY_FIELD_NAME, STREET_FIELD_NAME, HOUSE_FIELD_NAME, FLAT_FIELD_NAME,
            STATUS_FIELD_NAME, DATE_FIELD_NAME
    );

    private static final List<String> FIELDS_FOR_INSERT = Arrays.asList(
            ACC_ID_FIELD_NAME, EDITION_FIELD_NAME, TERM_FIELD_NAME, PRICE_FIELD_NAME, CITY_FIELD_NAME,
            STREET_FIELD_NAME, HOUSE_FIELD_NAME, FLAT_FIELD_NAME, STATUS_FIELD_NAME
    );

    private final String findByAccIdSql;

    private MethodArchiveDao(ConnectionPool pool) {
        super(pool, LOG);
        this.findByAccIdSql = format(SELECT_ALL_FROM, String.join(COMMA, getFields())) +
                getTableName() + SPACE + format(WHERE_ONE_FIELD, ACC_ID_FIELD_NAME);
    }

    public static MethodArchiveDao getInstance(){
        if(instance == null) {
            try {
                LOCK.lock();
                if(instance == null) {
                    instance = new MethodArchiveDao(ConnectionPool.lockingInstance());
                }
            } finally {
                LOCK.unlock();
            }
        } return instance;
    }

    @Override
    protected String getTableName() {
        return ARCHIVE_TABLE_NAME;
    }

    @Override
    protected String getInsertTableName() {
        return ARCHIVE_TABLE_NAME;
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
        return ID_FIELD_NAME;
    }

    @Override
    protected Archive extractResult(ResultSet rs) throws SQLException, EntityExtractionFailedException {
        try {
            return new Archive(
                    rs.getLong(ID_FIELD_NAME),
                    rs.getLong(ACC_ID_FIELD_NAME),
                    rs.getString(EDITION_FIELD_NAME),
                    rs.getInt(TERM_FIELD_NAME),
                    rs.getBigDecimal(PRICE_FIELD_NAME),
                    rs.getString(CITY_FIELD_NAME),
                    rs.getString(STREET_FIELD_NAME),
                    rs.getString(HOUSE_FIELD_NAME),
                    rs.getInt(FLAT_FIELD_NAME),
                    rs.getString(STATUS_FIELD_NAME),
                    rs.getTimestamp(DATE_FIELD_NAME));
        } catch (SQLException e) {
            LOG.error("sql exception occurred extracting edition from ResultSet", e);
            throw new EntityExtractionFailedException("could not extract entity", e);
        }
    }

    @Override
    protected void fillEntity(PreparedStatement statement, Archive archive) throws SQLException {
        statement.setLong(1, archive.getAccId());
        statement.setString(2, archive.getEditionName());
        statement.setInt(3, archive.getTerm());
        statement.setBigDecimal(4, archive.getPrice());
        statement.setString(5, archive.getCity());
        statement.setString(6, archive.getStreet());
        statement.setString(7, archive.getHouse());
        statement.setInt(8, archive.getFlat());
        statement.setString(9, archive.getStatusName());
    }

    @Override
    public Archive update(Archive entity) {
        return null;
    }

    @Override
    public List<Archive> findByAccId(Long accId) {
        try {
            return executePreparedForEntities(findByAccIdSql,
                    this::extractResultCatchingException,
                    st -> st.setLong(1, accId));
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return Collections.emptyList();
        }
    }
}
