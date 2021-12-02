package com.epam.jwd.subscription.dao;

import com.epam.jwd.subscription.db.ConnectionPool;
import com.epam.jwd.subscription.entity.Address;
import com.epam.jwd.subscription.entity.User;
import com.epam.jwd.subscription.exception.EntityExtractionFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.lang.String.join;

public class MethodAddressDao extends CommonDao<Address> implements AddressDao {

    private static final Logger LOG = LogManager.getLogger(MethodEditionDao.class);

    private static final String ADDRESS_TABLE_NAME = "address";
    private static final String ID_FIELD_NAME = "id";
    private static final String CITY_FIELD_NAME = "city";
    private static final String STREET_FIELD_NAME = "street";
    private static final String HOUSE_FIELD_NAME = "house";
    private static final String FLAT_FIELD_NAME = "flat";
    private static final String INSERT_INTO = "insert %s (%s)";
    private static final String VALUES = "values (?, ?, ?, ?)";
    protected static final String WHERE_FIELDS = "where %s = ? and %s = ? and %s = ? and %s = ?";
    private static final String COMMA = ", ";

    private static final List<String> FIELDS = Arrays.asList(
            ID_FIELD_NAME, CITY_FIELD_NAME, STREET_FIELD_NAME,
            HOUSE_FIELD_NAME, FLAT_FIELD_NAME
    );

    private static final List<String> FIELDS_FOR_INSERT = Arrays.asList(
            CITY_FIELD_NAME, STREET_FIELD_NAME,
            HOUSE_FIELD_NAME, FLAT_FIELD_NAME
    );

//    private final String insertSql;
    private final String selectByCSHFExpression;

    private MethodAddressDao(ConnectionPool pool) {
        super(pool, LOG);
//        this.insertSql = format(INSERT_INTO, getInsertTableName(), join(COMMA, getInsertFields()));
        this.selectByCSHFExpression = format(SELECT_ALL_FROM, String.join(COMMA, getFields())) +
                getTableName() + SPACE + format(WHERE_FIELDS, CITY_FIELD_NAME, STREET_FIELD_NAME,
                HOUSE_FIELD_NAME, FLAT_FIELD_NAME);
    }

    private static class Holder {
        public static final AddressDao INSTANCE = new MethodAddressDao(ConnectionPool.instance());
    }

    static AddressDao getInstance() {
        return MethodAddressDao.Holder.INSTANCE;
    }

    @Override
    protected String getTableName() {
        return ADDRESS_TABLE_NAME;
    }

    @Override
    protected String getInsertTableName() {
        return ADDRESS_TABLE_NAME;
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
    protected Address extractResult(ResultSet rs) throws SQLException, EntityExtractionFailedException {
        try {
            return new Address(
                    rs.getLong(ID_FIELD_NAME),
                    rs.getString(CITY_FIELD_NAME),
                    rs.getString(STREET_FIELD_NAME),
                    rs.getString(HOUSE_FIELD_NAME),
                    rs.getInt(FLAT_FIELD_NAME));
        } catch (SQLException e) {
            LOG.error("sql exception occurred extracting user from ResultSet", e);
            throw new EntityExtractionFailedException("could not extract entity", e);
        }
    }

//    @Override
//    public Address create(Address address) {
//        try {
//            final int rowsUpdated = executePreparedUpdate(insertSql, st -> fillEntity(st, address));
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
    public Optional<Address> selectByCSHFExpression(String city, String street, String house, Integer flat) {
        try {
            Address address = new Address(city, street, house, flat);
            return executePreparedForGenericEntity(selectByCSHFExpression,
                    this::extractResultCatchingException,
                    st -> fillEntity(st, address));
        } catch (InterruptedException e) {
            LOG.info("takeConnection interrupted", e);
            Thread.currentThread().interrupt();
            return Optional.empty();
        }
    }

    @Override
    protected void fillEntity(PreparedStatement statement, Address address) throws SQLException {
        statement.setString(1, address.getCity());
        statement.setString(2, address.getStreet());
        statement.setString(3, address.getHouse());
        statement.setInt(4, address.getFlat());
    }

    @Override
    public Address update(Address entity) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
