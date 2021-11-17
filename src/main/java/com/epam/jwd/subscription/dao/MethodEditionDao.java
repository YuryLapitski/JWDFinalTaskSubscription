package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.db.ConnectionPool;
import com.epam.jwd.subscription.entity.Category;
import com.epam.jwd.subscription.entity.Edition;
import com.epam.jwd.subscription.exception.EntityExtractionFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MethodEditionDao extends CommonDao<Edition> implements EditionDao {

    private static final Logger LOG = LogManager.getLogger(MethodEditionDao.class);

    private static final String EDITION_TABLE_NAME = "edition e join category c on c.id = e.cat_id";
    private static final String ID_FIELD_NAME = "e.id";
    private static final String NAME_FIELD_NAME = "e.name";
    private static final String CATEGORY_FIELD_NAME = "c.cat_name";

    private static final List<String> FIELDS = Arrays.asList(
            ID_FIELD_NAME, NAME_FIELD_NAME, CATEGORY_FIELD_NAME
    );

    private MethodEditionDao(ConnectionPool pool) {
        super(pool, LOG);
    }

    private static class Holder {
        public static final EditionDao INSTANCE = new MethodEditionDao(ConnectionPool.instance());
    }

    static EditionDao getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    protected String getTableName() {
        return EDITION_TABLE_NAME;
    }

    @Override
    protected List<String> getFields() {
        return FIELDS;
    }

    @Override
    protected String getIdFieldName() {
        return ID_FIELD_NAME;
    }

    @Override
    protected Edition extractResult(ResultSet rs) throws EntityExtractionFailedException {

        try {
            return new Edition(
                    rs.getLong(ID_FIELD_NAME),
                    rs.getString(NAME_FIELD_NAME),
                    Category.of(rs.getString(CATEGORY_FIELD_NAME)));
        } catch (SQLException e) {
            LOG.error("sql exception occured extracting edition from ResultSet", e);
            throw new EntityExtractionFailedException("could not extract entity", e);
        }
    }

    @Override
    protected void fillEntity(PreparedStatement statement, Edition entity) throws SQLException {

    }

    @Override
    public List<Edition> findByName(String model) {
        return null;
    }

    @Override
    public Optional<Long> findUserIdByEditionId(Long id) {
        return Optional.empty();
    }

    @Override
    public Edition create(Edition entity) {
        return null;
    }

    @Override
    public Optional<Edition> read(Long id) {
        return Optional.empty();
    }

    @Override
    public Edition update(Edition entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
