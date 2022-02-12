package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.db.ConnectionPool;
import com.epam.jwd.subscription.entity.Status;
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

public class MethodStatusDao extends CommonDao<Status> implements StatusDao {

    private static final Logger LOG = LogManager.getLogger(StatusDao.class);

    private static MethodStatusDao instance = null;
    private static final ReentrantLock LOCK = new ReentrantLock();

    private static final String STATUS_TABLE_NAME = "status";
    private static final String ID_FIELD_NAME = "id";
    private static final String STATUS_FIELD_NAME = "status_name";

    private static final List<String> FIELDS = Arrays.asList(
            ID_FIELD_NAME, STATUS_FIELD_NAME
    );

    private static final List<String> FIELDS_FOR_INSERT = Collections.singletonList(
            STATUS_FIELD_NAME
    );

    private MethodStatusDao(ConnectionPool pool) {
        super(pool, LOG);
    }

    public static MethodStatusDao getDaoInstance(){
        if(instance == null) {
            try {
                LOCK.lock();
                if(instance == null) {
                    instance = new MethodStatusDao(ConnectionPool.lockingInstance());
                }
            } finally {
                LOCK.unlock();
            }
        } return instance;
    }

    @Override
    protected String getTableName() {
        return STATUS_TABLE_NAME;
    }

    @Override
    protected String getInsertTableName() {
        return STATUS_TABLE_NAME;
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
    protected Status extractResult(ResultSet rs) throws SQLException, EntityExtractionFailedException {
        try {
            return new Status(
                    rs.getLong(ID_FIELD_NAME),
                    rs.getString(STATUS_FIELD_NAME));
        } catch (SQLException e) {
            LOG.error("sql exception occurred extracting user from ResultSet", e);
            throw new EntityExtractionFailedException("could not extract entity", e);
        }
    }

    @Override
    protected void fillEntity(PreparedStatement statement, Status entity) throws SQLException {

    }

    @Override
    public Status update(Status entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
