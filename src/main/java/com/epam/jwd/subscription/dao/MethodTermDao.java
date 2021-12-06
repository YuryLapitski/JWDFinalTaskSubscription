package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.db.ConnectionPool;
import com.epam.jwd.subscription.entity.Term;
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

public class MethodTermDao extends CommonDao<Term> implements TermDao {

    private static final Logger LOG = LogManager.getLogger(MethodTermDao.class);

    private static MethodTermDao instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final String TERM_TABLE_NAME = "term";
    private static final String ID_FIELD_NAME = "id";
    private static final String MONTHS_FIELD_NAME = "months";

    private static final List<String> FIELDS = Arrays.asList(
            ID_FIELD_NAME, MONTHS_FIELD_NAME
    );

    private static final List<String> FIELDS_FOR_INSERT = Collections.singletonList(
            MONTHS_FIELD_NAME
    );

    private MethodTermDao(ConnectionPool pool) {
        super(pool, LOG);
    }

    public static MethodTermDao getInstance(){
        if(instance == null) {
            try {
                LOCK.lock();
                if(instance == null) {
                    instance = new MethodTermDao(ConnectionPool.lockingInstance());
                }
            } finally {
                LOCK.unlock();
            }
        } return instance;
    }

    @Override
    protected String getTableName() {
        return TERM_TABLE_NAME;
    }

    @Override
    protected String getInsertTableName() {
        return TERM_TABLE_NAME;
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
    protected Term extractResult(ResultSet rs) throws SQLException, EntityExtractionFailedException {
        try {
            return new Term(
                    rs.getLong(ID_FIELD_NAME),
                    rs.getInt(MONTHS_FIELD_NAME));
        } catch (SQLException e) {
            LOG.error("sql exception occurred extracting user from ResultSet", e);
            throw new EntityExtractionFailedException("could not extract entity", e);
        }
    }

    @Override
    protected void fillEntity(PreparedStatement statement, Term entity) throws SQLException {

    }

    @Override
    public Term update(Term entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
